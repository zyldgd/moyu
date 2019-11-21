package com.zing.moyu.dao;

import com.zing.moyu.JsonElement.MessageJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class MessagesDao {

    public static boolean createMessageTable(String userId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_MESSAGES_DB);
        Connection connection = dbApi.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS `" + userId + "`(" +
                "  messageHash varchar(32)      not null primary key," +
                "  contactType varchar(255)     not null," +
                "  `from`      int(32) unsigned not null," +
                "  `to`        int(32) unsigned not null," +
                "  `date`      date             not null," +
                "  `time`      time             not null," +
                "  content     TEXT" +
                ") charset = utf8mb4;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean dropMessageTable(String userId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_MESSAGES_DB);
        Connection connection = dbApi.getConnection();
        String sql = "DROP TABLE IF EXISTS `" + userId + "`";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static LinkedList<MessageJson> query(int limit) {
        return null;
    }

    private static boolean putMessageTo(MessageJson messageJson, String userId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_MESSAGES_DB);
        Connection connection = dbApi.getConnection();
        String sql = "INSERT INTO `" + userId + "`" + "(messageHash, content, `from`, `to`, contactType, `date`, `time`) VALUES(?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, messageJson.getMessageHash());
            preparedStatement.setString(2, messageJson.getContent());
            preparedStatement.setString(3, messageJson.getFrom());
            preparedStatement.setString(4, messageJson.getTo());
            preparedStatement.setString(5, messageJson.getContactType());
            preparedStatement.setString(6, messageJson.getDate());
            preparedStatement.setString(7, messageJson.getTime());
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean insert(MessageJson messageJson) {
        if (null == messageJson) {
            return false;
        }
        putMessageTo(messageJson, messageJson.getFrom());
        if (messageJson.getContactType().equals("friend")) {
            putMessageTo(messageJson, messageJson.getTo());
        } else {
            // TODO 首先得到群聊的成员，然后一个个存入消息
        }
        return true;
    }

    public static boolean delete(String userId, String messageId) {
        // TODO  删除消息
        return true;
    }

    public static void main(String[] args) {
        DBApi dbApi = new DBApi(DBApi.MOYU_MESSAGES_DB);
        Connection connection = dbApi.getConnection();
        String sql = "INSERT INTO `" + 1 + "`" + "(messageHash, content, `from`, `to`, contactType, `date`, `time`) VALUES(?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "1233");
            preparedStatement.setString(2, "\uD83D\uDE28");
            preparedStatement.setString(3, "123");
            preparedStatement.setString(4, "123");
            preparedStatement.setString(5, "123");
            preparedStatement.setString(6, "1999-08-08");
            preparedStatement.setString(7, "00:00:00");
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
