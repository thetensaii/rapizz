package com.tdt.core;

import java.sql.*;

public class  Database {
    private static Database instance;

    Connection conn;

    static final String HOST = "185.216.25.56";
    static final String DATABASE = "rapizz";
    static final String USER = "rapizz";
    static final String PASSWORD = "rapizz";


    private Database(Connection conn) {
        this.conn = conn;
    }

    public static Database getInstance() {
        try {
            if (instance == null || instance.conn.isClosed()) {
                Connection conn = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE + "?" +
                        "user=" + USER + "&password=" + PASSWORD + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                instance = new Database(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return instance;
    }

    public ResultSet query(String query) {

        try {
            return conn.createStatement().executeQuery(query);
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }

        return null;
    }

    public PreparedStatement createPreparedStatement(String query) {
        try {
            return conn.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement createPreparedStatement(String query, int statement) {
        try {
            return conn.prepareStatement(query, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
