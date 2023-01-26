package com.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
@Slf4j
@EnableKafka
@RequiredArgsConstructor
public class ProdConsConfiguration {

    private final KafkaProperties props;

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return configProps;
    }

    @Bean
    public KafkaTemplate<String, String> stringTemplate() {
        ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(producerConfigs());
        return new KafkaTemplate<>(pf,
                Collections.singletonMap(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class));
    }

    @Bean
	public KafkaTemplate<Integer, String> integerStringTemplate() {
    	
//    	Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        ProducerFactory<Integer, String> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
    	
        return new KafkaTemplate<>(producerFactory());
	}

    /**
     * RoutingKafkaTemplate to select the producer at runtime, based on the destination topic name.
     * The routing template does not support transactions, execute, flush,
     * or metrics operations because the topic is not known for those operations.
     *
     * The template requires a map of java.util.regex.Pattern to ProducerFactory<Object, Object> instances.
     * This map should be ordered (e.g. a LinkedHashMap) because it is traversed in order;
     * you should add more specific patterns at the beginning.
     *
     *
     *              routingTemplate.send("one", "thing1");
     *             routingTemplate.send("two", "thing2".getBytes());
     */
    //@Bean
    public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context,
                                                ProducerFactory<Object, Object> pf) {

        // Clone the PF with a different Serializer, register with Spring for shutdown
        Map<String, Object> configs = new HashMap<>(pf.getConfigurationProperties());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        DefaultKafkaProducerFactory<Object, Object> bytesPF = new DefaultKafkaProducerFactory<>(configs);
        context.registerBean(DefaultKafkaProducerFactory.class, "bytesPF", bytesPF);

        Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
        map.put(Pattern.compile("two"), bytesPF);
        map.put(Pattern.compile(".+"), pf); // Default PF with StringSerializer
        return new RoutingKafkaTemplate(map);
    }

    /**
     *
     * ReplyingKafkaTemplate to provide request/reply semantics
     * RequestReplyFuture<K, V, R> sendAndReceive(ProducerRecord<K, V> record);
     */
    //@Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
            ProducerFactory<String, String> pf,
            ConcurrentMessageListenerContainer<String, String> repliesContainer) {

        return new ReplyingKafkaTemplate<>(pf, repliesContainer);
    }

    //@Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {

        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                containerFactory.createContainer("kReplies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

}
