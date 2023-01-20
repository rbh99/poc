package com.example;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
@RequiredArgsConstructor
public class KafkaConsumer {
	
	private final KafkaProducer producer;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
    private String payload;
    
    @Value("${test.logtopic}")
    private String logtopic;

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());
        payload = consumerRecord.toString();
        latch.countDown();
        producer.send(logtopic, payload);
        
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
    
    
    

}
	