package com.example;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
public class WordCountTopologyTest {
	
	@Autowired
	private WordProcessor wordCountProcessor;
	
	@Autowired
	StreamsBuilderFactoryBean factoryBean;
	
	@Value("${test.topic}")
    private String topic;
	
	@Value("${test.logtopic}")
    private String logtopic;
	
	@Test
	void givenInputMessages_whenProcessed_thenWordCountIsProduced() {
	    StreamsBuilder streamsBuilder = new StreamsBuilder();
	    wordCountProcessor.buildPipeline(streamsBuilder);
	    Topology topology = streamsBuilder.build();

	    
	    try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, new Properties())) {
	        
	    	TestInputTopic<String, String> inputTopic = topologyTestDriver
	          .createInputTopic(topic, new StringSerializer(), new StringSerializer());
	        
	        TestOutputTopic<String, Long> outputTopic = topologyTestDriver
	          .createOutputTopic(topic, new StringDeserializer(), new LongDeserializer());

	        inputTopic.pipeInput("key", "hello world");
	        inputTopic.pipeInput("key2", "hello");

	        assertThat(outputTopic.readKeyValuesToList())
	          .containsExactly(
	            KeyValue.pair("hello", 1L),
	            KeyValue.pair("world", 1L),
	            KeyValue.pair("hello", 2L)
	          );
	    }
	    
	    
	    //check 
	    KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
	    ReadOnlyKeyValueStore<String, Long> counts = 
	    		kafkaStreams.store(
	      StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore())
	    );
	    log.info("hello appears {} times", counts.get("hello"));
	    assertEquals(2, counts.get("hello"));
	    
	    
	}

}
