package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import com.example.consumer.KafkaConsumer;
import com.example.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, 
	brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedProducerTest {
	
	@Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() 
      throws Exception {
        String data = "Sending with our own simple KafkaProducer";
        
        producer.send(topic, data);
        
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(consumer.getPayload(), containsString(data));
    }
	
	//@Test
	void testProducer_consumer() {
		String data = "aaa";
        
        producer.send(topic, data);
        
        //boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        //assertTrue(messageConsumed);
        //assertThat(consumer.getPayload(), containsString(data));
	}

}
