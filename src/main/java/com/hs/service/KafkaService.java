package com.hs.service;

import com.hs.constants.Constants;
import com.hs.util.PGUtil;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import scala.sys.Prop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaService {


    public Properties getProperties() throws IOException {
        Properties properties = new Properties();

        InputStream inputStream;
        inputStream = getClass().getClassLoader().getResourceAsStream("kafka.properties");

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file not found in the classpath");
        }

        return properties;

    }

    public void createTopic() throws IOException, ExecutionException, InterruptedException {

        Properties properties = getProperties();
        AdminClient adminClient = AdminClient.create(properties);
        NewTopic newTopic = new NewTopic("test", 1, (short) 1); //new NewTopic(topicName, numPartitions, replicationFactor)

        List<NewTopic> newTopics = new ArrayList<NewTopic>();
        newTopics.add(newTopic);

        adminClient.createTopics(newTopics);
        System.out.println("Topic created");


        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true); // includes internal topics such as __consumer_offsets
        ListTopicsResult topics = adminClient.listTopics(options);
        Set<String> currentTopicList = topics.names().get();

        for (String topicName : currentTopicList) {
            System.out.println("topicName is: " + topicName);
        }
        adminClient.close();
    }

    //function to write to topic
    public void writeToTopic(int recordCount) throws IOException {

        String topicName = PGUtil.getProperty(Constants.TOPIC_NAME);

        Properties properties = getProperties();
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);


        //get the max timestamp that needs to be sent to kafka to be stored for future calls
        PostgresService postgresService = new PostgresService();
        String maxTime = postgresService.fetchMaxTimestamp();
        //send the count as value and the "count" word as key
        producer.send(new ProducerRecord<String, String>(topicName,
                "count",recordCount+","+maxTime.trim()));
        System.out.println("Message sent successfully" );
        producer.close();
    }


}
