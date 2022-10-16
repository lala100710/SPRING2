package org.example.socket;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        FileInputStream fin = null;
        try {
            socket = new Socket("0.0.0.0", 5000);
            int choice = 1;
            while (choice != 0) {
                BufferedReader buf = new BufferedReader(
                        new InputStreamReader(System.in));
                System.out.print("請輸入1(新增) 2(刪除) 3(修改) 4(查詢) 0(離開): ");

                choice = Integer.parseInt(buf.readLine());
                String content = "";
                switch (choice) {

                    case 1:
                        //user.dir 取路徑
                        content = loadFile("src/main/resources/file/insert.json");
                        passValueToServer(socket, choice + content);
                        break;
                    case 2:
                        content = loadFile("src/main/resources/file/delete.json");
                        passValueToServer(socket, choice + content);
                        break;
                    case 3:

                        content = loadFile("src/main/resources/file/update.json");
                        passValueToServer(socket, choice + content);
                        break;
                    case 4:
                        content = loadFile("src/main/resources/file/search.json");
                        passValueToServer(socket, choice + content);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("請輸入1-4");
                }
                if (choice != 0){
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    System.out.println("result" + objectInputStream.readObject());
                }
            }


        } catch (IOException e) {
            System.out.println("連線失敗");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static String loadFile(String path) {
        Gson gson = new Gson();
        Object arr = null;
        try {
            arr = gson.fromJson(new FileReader(path), Object.class);
        } catch (IOException e) {
            System.out.println("查無此檔");
        }
        return arr.toString();
    }

    public static void passValueToServer(Socket socket, String content) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("passValueToServer Error :" + e.getMessage());
        }
    }


}
