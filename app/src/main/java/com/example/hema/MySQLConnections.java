package com.example.hema;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnections {
    private String driver = "";
    private String dbURL = "";
    private String user = "";
    private String password = "";
    private static MySQLConnections connection = null;
    private MySQLConnections() throws Exception {
        driver = "com.mysql.jdbc.Driver";
        dbURL = "jdbc:mysql://121.196.220.139:3306/android?useSSL=false&&characterEncoding=utf-8";//&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai
        user = "root";
        password = "123456";
        System.out.println("dbURL:" + dbURL);
    }
    public static Connection getConnection() {
        Connection conn = null;
        if (connection == null) {
            try {
                connection = new MySQLConnections();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            Class.forName(connection.driver);
            conn = DriverManager.getConnection(connection.dbURL,
                    connection.user, connection.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
