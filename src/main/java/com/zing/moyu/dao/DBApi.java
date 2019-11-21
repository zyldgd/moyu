package com.zing.moyu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBApi {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MOYU_DB = "jdbc:mysql://172.20.71.100:3306/MOYU_DB?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String MOYU_MESSAGES_DB = "jdbc:mysql://172.20.71.100:3306/MOYU_MESSAGES_DB?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String MOYU_CONTACTS_DB = "jdbc:mysql://172.20.71.100:3306/MOYU_CONTACTS_DB?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String MOYU_MEMBERS_DB = "jdbc:mysql://172.20.71.100:3306/MOYU_MEMBERS_DB?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";

    private static String DB_URL;
    private static final String USER = "zing";
    private static final String PASS = "zing";

    private Connection connection = null;

    public DBApi(String dbName){
        this.DB_URL = dbName;
        this.connect();
    }

    private boolean connect(){
        System.out.println("[INFO] 连接数据库...");
        try {
            Class.forName(JDBC_DRIVER); // 注册 JDBC 驱动
            connection = DriverManager.getConnection(DB_URL, USER, PASS);// 打开链接
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Connection getConnection(){
        return connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getPreparedStatement(String sql){
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
