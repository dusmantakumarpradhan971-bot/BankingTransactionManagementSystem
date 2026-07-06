package com.banking.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER = "system";
    private static final String PASSWORD = "Dusmanta123";

    public static Connection getConnection() throws Exception {

        Class.forName("oracle.jdbc.OracleDriver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }
}