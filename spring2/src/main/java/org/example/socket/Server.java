package org.example.socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.example.persistence.dto.request.AddMgnMgniRequest;
import org.example.persistence.dto.request.DeleteDataRequest;
import org.example.persistence.dto.request.QueryMgnMgniRequest;
import org.example.persistence.dto.request.UpdateDataRequest;
import org.example.service.MgnMgniService;
import org.example.service.MgnMgniServiceImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private static int serverPort = 5000;
    private static Logger logger = Logger.getLogger(Server.class);


    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            //設定Ip
            serverSocket = new ServerSocket(serverPort);
            serverSocket.setReuseAddress(true);

            while (true) {
                System.out.println("server 已啟動 :" + serverPort + " : " + serverSocket.getLocalSocketAddress());
                Socket clientSocket = serverSocket.accept();
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
                System.out.println("收到客戶端連線  ip: " + clientAddress);
                //log 嚴重性

                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("server 斷線");
                    logger.info("斷線" + e.getMessage());
                }
            }
            executorService.shutdown();
        }
    }

    //格式統一
    public static <T> T convertJson(String text, Class<T> object) {
        //格式錯誤 處理
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(text));
        reader.setLenient(true);
        T arr = gson.fromJson(reader, object);
        String error = checkDataValidation(object);
        if (error != null) {
            return arr;
        }
        return null;
    }

    public static void crud(String operation, String jsonText, Socket socket) throws IOException {

        MgnMgniService mgnMgniService = new MgnMgniServiceImpl();
        switch (operation) {
            case "1":
                AddMgnMgniRequest addMgnMgniRequest = convertJson(jsonText, AddMgnMgniRequest.class);

                passValueToClient(socket, mgnMgniService.create(addMgnMgniRequest).toString());
                break;
            case "2":
                DeleteDataRequest deleteDataRequest = convertJson(jsonText, DeleteDataRequest.class);
                passValueToClient(socket, mgnMgniService.delete(deleteDataRequest.getMgniId()).toString());
                break;
            case "3":
                UpdateDataRequest updateDataRequest = convertJson(jsonText, UpdateDataRequest.class);
                passValueToClient(socket, mgnMgniService.update(updateDataRequest).toString());
                break;
            case "4":
                QueryMgnMgniRequest id = convertJson(jsonText, QueryMgnMgniRequest.class);
                passValueToClient(socket, mgnMgniService.findById(id.getMgniId()).toString());

                break;
        }

    }

    public static String checkDataValidation(Object object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> result = validator.validate(object);
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<Object> mgnMgniConstraintViolation : result) {
            stringBuilder.append(mgnMgniConstraintViolation.getMessage()).append("\n");
            System.out.println(mgnMgniConstraintViolation.getMessage());
        }
        return stringBuilder.toString();
    }

    @SneakyThrows
    public static void passValueToClient(Socket socket, String content) {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);
            outputStream.flush();

        } catch (IOException e) {
            System.out.println("passValueToClient Error :" + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;


        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }


        @SneakyThrows
        @Override
        public void run() {
            InputStream inputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                while (true) {
                    inputStream = clientSocket.getInputStream();
                    objectInputStream = new ObjectInputStream(inputStream);
                    String jsonText = (String) objectInputStream.readObject();
                    // socket 完整資料判斷
                    crud(jsonText.substring(0, 1), "{" + jsonText.substring(2), clientSocket);
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
