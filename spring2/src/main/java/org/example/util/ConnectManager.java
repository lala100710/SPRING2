package org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectManager {
    private static ConnectManager instance;
    private Connection connection;

    public ConnectManager() {

        try {
            Properties properties = new Properties();
            // resource bound
            properties.load(new FileInputStream("src/main/resources/dbconfig.properties"));
            String url = properties.getProperty("url");
            String user = properties.getProperty("username");
            String passwd = properties.getProperty("passwd");
            this.connection = DriverManager.getConnection(url,user,passwd);
            System.out.println("success");
        } catch (SQLException e) {
            System.out.println("無法連接資料庫" + e.getSQLState());

        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        if (this.connection !=null){
            try {
                connection.close();
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConnectManager getInstance() {

        try {
            if (instance == null || instance.connection.isClosed()) {

                    if (instance == null || instance.connection.isClosed()) {
                        instance = new ConnectManager();
                    }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
