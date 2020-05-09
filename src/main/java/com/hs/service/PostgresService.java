package com.hs.service;


import com.hs.constants.Constants;
import com.hs.util.PGUtil;

import java.sql.*;

public class PostgresService {

    public Integer runQuery(String maxTimestamp) {

        int count = 0;
        Connection conn = null;
        try {


            String hostName = PGUtil.getProperty(Constants.PG_HOST);
            String username = PGUtil.getProperty(Constants.PG_USERNAME);
            String password = PGUtil.getProperty(Constants.PG_PASSWORD);
            String port = PGUtil.getProperty(Constants.PG_PORT);
            String databaseName = PGUtil.getProperty(Constants.PG_DATABASE);

            System.out.println("Establishing connection to Postgres....");
//            System.out.println(hostName+", "+username+", "+ password +", "+ port +", "+ databaseName);
            conn = DriverManager.getConnection("jdbc:postgresql://" + hostName + ":" + port + "/" + databaseName
                    , username, password);

            System.out.println("=======Connection to postgres established======");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM public.orders where " +
                    "updated_at > '"+maxTimestamp+"'");

            while (rs.next()) {
                count = rs.getInt(1);
                System.out.println("count is: " + count);
            }




        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create JDBC db connection " + e.toString() + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

}