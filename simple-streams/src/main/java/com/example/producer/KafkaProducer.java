package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;


/** a simple wrapper around kafkaTemplate */

@Component
@Slf4j
public class KafkaProducer {
	
	@Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public void send(String topic, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        CompletableFuture<SendResult<Integer,String>> future = kafkaTemplate.send(topic, payload);
        future.whenComplete((sr, th) -> {
            if (th != null){
                log.error("Sending throws ",th);
            }else{
                log.info("sendResult {}",sr);
            }
        });
    }

}
