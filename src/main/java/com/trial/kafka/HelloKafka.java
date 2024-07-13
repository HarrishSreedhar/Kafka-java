package com.trial.kafka;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.twitter.hbc.ClientBuilder;

import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import static com.trial.kafka.AppConfigs.bootstrapServer;


public class HelloKafka {

    private String consumerKey = "";
    private String consumerSecret = "";
    private String token = "";
    private String secret = "";

    List<String> topics = Arrays.asList("geeksforgeeks", "java", "kafka");

    public static void main() {
        new HelloKafka().start();
    }

    public void start() {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(1000);

        // Create Twitter Client
        Client twitterClient = createTwitterClient(msgQueue);

        twitterClient.connect();

        KafkaProducer producer = createKafkaProducer();

        sendTweetsToKafka(msgQueue, twitterClient, producer);

    }

    private void sendTweetsToKafka(BlockingQueue<String> msgQueue, Client twitterClient, KafkaProducer producer) {

        while (!twitterClient.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                twitterClient.stop();
            }
            if (msg != null) {
                producer.send(new ProducerRecord<>("twitter_tweets", null, msg), (recordMetadata, e) -> {
                    if (e != null) {
                        System.out.println(e.getMessage());
                    }
                });
            }
        }
    }

    private KafkaProducer<String, String> createKafkaProducer() {
        // Create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create the Producer
        return new KafkaProducer<>(properties);

    }

    private Client createTwitterClient(BlockingQueue<String> msgQueue) {

        Hosts hosts = new HttpHosts(Constants.USERSTREAM_HOST);
        StatusesFilterEndpoint statusFilterEndpoint = new StatusesFilterEndpoint();

        statusFilterEndpoint.trackTerms(topics);

        // These secrets should be read from a config file
        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        ClientBuilder builder = new ClientBuilder()
                .name("twitter-demo-client-1")
                .hosts(hosts)
                .authentication(auth)
                .endpoint(statusFilterEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }
}