package com.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableKafka
@EnableKafkaStreams
public class MyKafkaStreamConfiguration {
	
	
	public static final String TEST_SIMPLE_STREAM = "test-simple-stream";


	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;


	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testStreams");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer customizer() {
        return fb -> fb.setStateListener((newState, oldState) -> {
            log.debug("State transition from {} to {} " ,oldState,  newState);
        });
    }
    

//    @Bean
//    public KStream<Integer, String> kStream(StreamsBuilder kStreamBuilder) {
//        KStream<Integer, String> stream = kStreamBuilder.stream(TEST_SIMPLE_STREAM);
//        stream.mapValues( new ValueMapper<String, String>() {
//        	
//					@Override
//					public String apply(String value) {
//						log.debug("to uppercase {}",value);
//						return value.toUpperCase();
//					}
//                })
//                .groupByKey()
//                .windowedBy(TimeWindows.of(Duration.of(1, ChronoUnit.SECONDS)))
//				.reduce((String value1, String value2) -> value1 + value2)
//				//the new KTable to stream
//				.toStream()
//                .map((windowedId, value) -> new KeyValue<>(windowedId.key(), value))
//                .filter((i, s) -> s.length() > 5)
//                .peek((k,v) -> log.info("from the stream key = {} value = {} ", k, v ))
//                //.to("streamingTopic2")
//               
//                
//                ;
//
//        //stream.print();
//
//        return stream;
//    }
	
    @Bean
	public KafkaTemplate<Integer, String> kafkaTemplate() {
    	
    	Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<Integer, String> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
    	
	    return new KafkaTemplate<>(producerFactory);
	}
	
	
}
