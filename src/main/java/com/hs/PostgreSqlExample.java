package com.hs;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlExample {

    public String runQuery() {

        StringBuilder builder = new StringBuilder();
        // auto close connection
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "password")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                builder.append("connnotnull");
            } else {
                System.out.println("Failed to make connection!");
                builder.append("connnull");
            }
        return builder.toString();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}