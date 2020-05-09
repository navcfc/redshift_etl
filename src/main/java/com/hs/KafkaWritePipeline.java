package com.hs;// Java program to demonstrate
//schedule method calls of Timer class 

import com.hs.service.FileService;
import com.hs.service.KafkaService;
import com.hs.service.PostgresService;
import com.hs.util.PGUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

class Helper extends TimerTask {
    public static int i = 0;

    public void run() {

        System.out.println("Timer ran " + ++i);
        //Fetch the last updated timestamp from the file
        FileService fileService = new FileService();
        String maxTimestamp = null;
        try {
            maxTimestamp = fileService.fetchTimestamp();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //check the count of changes from the last timestamp
        PostgresService postgresService = new PostgresService();
        int recordsUpdated = postgresService.runQuery(maxTimestamp);


        if (recordsUpdated > 0) {
            KafkaService kafkaTest = new KafkaService();
            try {
                //write the changed count to a kafka topic
                kafkaTest.writeToTopic(recordsUpdated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

public class KafkaWritePipeline {
    public static void main(String[] args) {

//      check arguments
        if (args.length != 1) {
            System.out.println("Invalid number of imput params: Please follow the" +
                    "ordering: 1.Properties file path");
            System.exit(-1);
        }
        String filePath = args[0];

        //load configurations
        PGUtil.loadConfigFile(filePath);


        //set a timer
        Timer timer = new Timer();
        TimerTask task = new Helper();

        //set a timer of 2 and a half minutes with a first time delay of 30 seconds
        timer.schedule(task, 30000, 150000);

    }
} 