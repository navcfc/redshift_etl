package com.hs.service;


import com.hs.constants.Constants;
import com.hs.util.PGUtil;

import java.sql.*;

public class PostgresService {

    public Integer runQuery(String maxTimestamp) {

        int count = 0;
        Connection conn = null;
        try {


            //fetch props
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
            System.out.println("maxTimestamp from the file is: " + maxTimestamp);
            //run the count query to get the updated no. of records
            String countQuery = "SELECT count(*) FROM public.orders where " +
                    "updated_at > '" + maxTimestamp + "'";
            System.out.println("countQuery is : " + countQuery);

            //execute the count query
            ResultSet rs = stmt.executeQuery(countQuery);

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


    public String fetchMaxTimestamp(){
        Connection conn = null;
        Timestamp maxTime = null;
        try {


            //fetch props
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
            //run the max timestamp query to get the max timestamp
            String maxQUery = "SELECT max(updated_at) FROM public.orders ";
            System.out.println("maxQuery is : " + maxQUery);

            //execute the count query
            ResultSet rs = stmt.executeQuery(maxQUery);

            while (rs.next()) {
                maxTime = rs.getTimestamp(1);
                System.out.println("Max time is: " + maxTime.toString());
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

        return maxTime.toString();
    }

}