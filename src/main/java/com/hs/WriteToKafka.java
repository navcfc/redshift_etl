package com.hs;// Java program to demonstrate
//schedule method calls of Timer class 

import com.hs.service.KafkaTest;
import com.hs.service.PostgreSqlExample;
import com.hs.util.PGUtil;

import java.io.IOException;
import java.nio.file.FileSystemAlreadyExistsException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//class Helper extends TimerTask
//{
//    public static int i = 0;
//    public void run()
//    {
////        PostgreSqlExample postgreSqlExample = new PostgreSqlExample();
////        String kafkaMessage = postgreSqlExample.runQuery();
////        System.out.println("\nkafkaMessage: " + kafkaMessage);
//        System.out.println("Timer ran " + ++i);
//    }
//}

public class WriteToKafka
{
    public static void main(String[] args) throws IOException {

        //working
//        if(args.length != 1){
//            System.out.println("Invalid number of imput params: Please follow the" +
//                    "ordering: 1.Properties file path");
//            System.exit(-1);
//        }
//        String filePath = args[0];
//        PGUtil.loadConfigFile(filePath);
//
//        PostgreSqlExample postgreSqlExample = new PostgreSqlExample();
//        String kafkaMessage = postgreSqlExample.runQuery();
//        System.out.println("\nkafkaMessage: " + kafkaMessage);


        KafkaTest kafkaTest = new KafkaTest();
//        try {
//            kafkaTest.createTopic();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        kafkaTest.writeToTopic("test");
//       Timer timer = new Timer();
//        TimerTask task = new Helper();
//
//        timer.schedule(task, 2000, 5000);

    }
} 