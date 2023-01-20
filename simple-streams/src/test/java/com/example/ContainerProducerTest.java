package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import lombok.extern.slf4j.Slf4j;


@Testcontainers
@ActiveProfiles("test")
@SpringBootTest //(properties = {"bootstrap.servers = [PLAINTEXT://localhost:9093]", "kafka.bootstrapAddress = localhost:9093"})
@DirtiesContext
@Slf4j
public class ContainerProducerTest {
	
	// 6.2.1 instead 5.4.3
	@ClassRule
	@Container
    public static KafkaContainer kafkaContainer = 
      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
	
	@DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

	@Autowired
	private KafkaConsumer consumer;

	@Autowired
	private KafkaProducer producer;

	@Value("${test.topic}")
	private String topic;

	@Test
	public void givenKafkaDockerContainer_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
		
		assertThat(kafkaContainer.isRunning()).isTrue();
		log.info("bootstrap servers {}", kafkaContainer.getBootstrapServers());
		
		// topic  exits?
		
		
		String data = "Sending with our own simple KafkaProducer";

		producer.send(topic, data);

		boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

		assertTrue(messageConsumed);
		assertThat(consumer.getPayload(), containsString(data));
		
	}

	
	//@Bean
    //@Order(-1)
    //public NewTopic createNewTopic() {
   //     return new NewTopic(topic, 1, (short)1);
    //}
}
