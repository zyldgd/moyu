package com.zing.vchat.dao;

import com.zing.vchat.JsonElement.ContactJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactsDao {
    public static boolean createContactsTable(String userId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
        Connection connection = dbApi.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS `" + userId + "`(" +
                "  `contactId` VARCHAR(64) NOT NULL PRIMARY KEY," +
                "  `peerId` INT(32) UNSIGNED NOT NULL," +
                "  `type` VARCHAR(255) NOT NULL," +
                "  `remarkName` VARCHAR(255)" +
                ") CHARACTER SET = utf8mb4";
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

    public static boolean dropContactsTable(String userId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
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

    public static boolean insert(String userId, ContactJson contactJson) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
        Connection connection = dbApi.getConnection();
        String sql = "INSERT INTO `" + userId + "`(contactId, peerId, type, remarkName) VALUES(?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contactJson.getType() + "^" + contactJson.getPeerId());
            preparedStatement.setString(2, contactJson.getPeerId());
            preparedStatement.setString(3, contactJson.getType());
            preparedStatement.setString(4, contactJson.getRemarkName());
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean update(String userId, ContactJson contactJson) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
        Connection connection = dbApi.getConnection();
        String sql = "UPDATE `" + userId + "` SET remarkName = ? WHERE contactId = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contactJson.getRemarkName());
            preparedStatement.setString(2, contactJson.getContactId());
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(String userId, String contactId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
        Connection connection = dbApi.getConnection();
        String sql = "DELETE FROM `" + userId + "` WHERE contactId = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contactId);
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ContactJson query(String userId, String peerId, String type) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
        Connection connection = dbApi.getConnection();
        String sql = "SELECT * FROM `" + userId + "` WHERE contactId = ?";
        ContactJson contactJson = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type+ "^" + peerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contactJson = new ContactJson(resultSet.getString("contactId"),
                        resultSet.getString("peerId"),
                        resultSet.getString("type"),
                        resultSet.getString("remarkName"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return contactJson;
    }

    public static LinkedList<ContactJson> getContacts(String userId) {
        DBApi dbApi = new DBApi(DBApi.MOYU_CONTACTS_DB);
        Connection connection = dbApi.getConnection();
        String sql = "SELECT * FROM `" + userId + "`";
        LinkedList<ContactJson> ContactJsons = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ContactJsons.add(new ContactJson(resultSet.getString("contactId"),
                        resultSet.getString("peerId"),
                        resultSet.getString("type"),
                        resultSet.getString("remarkName")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return ContactJsons;
        }
        return ContactJsons;
    }

    public static void main(String[] args) {
        //ContactsDao.createContactsTable("123");
        //ContactsDao.update("123", new ContactJson("5","100004","friend", "test5"));

        //ContactsDao.getContacts("100000");
    }

}
