package com.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogKafkaConsumer {

	@KafkaListener(topics = {"${test.logtopic}", "${test.logtopic}"} )
    public void logtopic(ConsumerRecord<?, ?> consumerRecord) {
        log.info("LOG Consumer received payload='{}'", consumerRecord.toString());
    }

}
