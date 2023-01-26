package com.example;


import com.example.stream.WordProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.Stores;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

	//@BeforeEach
	 void ourCleanUp() throws IOException{
		final File baseDir = new File("C:/Users/bogdan/AppData/Local/Temp/kafka-streams");
		final File stateDir = new File(baseDir, "testStreams");
		//Files.deleteIfExists(Paths.get("C:/Users/bogdan/AppData/Local/Temp/kafka-streams", "testStreams"));
		Path pathToBeDeleted = Paths.get("C:/Users/bogdan/AppData/Local/Temp/kafka-streams", "testStreams");
		boolean exist = Files.exists(Paths.get("C:/Users/bogdan/AppData/Local/Temp/kafka-streams", "testStreams"));
		log.info("path exists: {}", exist);



		Files.walk(pathToBeDeleted)
				.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(File::delete);

		//boolean deleted = FileSystemUtils.deleteRecursively(stateDir);
		//log.info("path deleted: {}", deleted);
		//assertFalse(Files.exists(pathToBeDeleted));


	}
	
	@Test
	void givenInputMessages_whenProcessed_thenWordCountIsProduced()  {
		Properties properties = new Properties();
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "testapp");
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(StreamsConfig.DEFAULT_DSL_STORE_CONFIG, StreamsConfig.IN_MEMORY);
		StreamsConfig streamsConfig = new StreamsConfig(properties);

	    StreamsBuilder streamsBuilder = new StreamsBuilder(new TopologyConfig(streamsConfig));

		wordCountProcessor.buildPipeline(streamsBuilder);
	    Topology topology = streamsBuilder.build();


		//Properties properties =  new Properties();
		//properties.put(StreamsConfig.STATE_DIR_CONFIG, "F:/proiecte/poc/simple-streams/tmp");
	    
	    try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, properties)) {
	        
	    	TestInputTopic<String, String> inputTopic = topologyTestDriver
	          .createInputTopic(topic, new StringSerializer(), new StringSerializer());
	        
	        TestOutputTopic<String, Long> outputTopic = topologyTestDriver
	          .createOutputTopic(logtopic, new StringDeserializer(), new LongDeserializer());

	        inputTopic.pipeInput("key", "hello world");
	        inputTopic.pipeInput("key2", "hello");

			//log.debug("output topic {}",outputTopic.readKeyValuesToList());

	        assertThat(outputTopic.readKeyValuesToList())
	          .containsExactly(
	            KeyValue.pair("hello", 1L),
	            KeyValue.pair("world", 1L),
	            KeyValue.pair("hello", 2L)
	          );
	    }
	    
	    
	    //check 
		/*KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();


	    ReadOnlyKeyValueStore<String, Long> counts =
	    		kafkaStreams.store(StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore()));
	    log.info("hello appears {} times", counts.get("hello"));
	    assertEquals(2, counts.get("hello"));*/



	}

	/*
	@Test
	public void shouldTestWordCountWithInteractiveQueries() throws InterruptedException {

		final Serde<String> stringSerde = Serdes.String();
		final StreamsBuilder builder = new StreamsBuilder();
		final KStream<String, String>
				textLines = builder.stream(TEXT_LINES_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

		final KGroupedStream<String, String> groupedByWord = textLines
				.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
				.groupBy((key, word) -> word, Grouped.with(stringSerde, stringSerde));

		groupedByWord.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("WordCountsStore")
				.withValueSerde(Serdes.Long()));

		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-interactive-queries");

		final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
		streams.cleanUp();
		streams.start();

		producer.send(new ProducerRecord<String, String>(TEXT_LINES_TOPIC, "1", TEXT_EXAMPLE_1));
		producer.send(new ProducerRecord<String, String>(TEXT_LINES_TOPIC, "2", TEXT_EXAMPLE_2));

		Thread.sleep(2000);
		ReadOnlyKeyValueStore<String, Long> keyValueStore =
				streams.store(StoreQueryParameters.fromNameAndType(
						"WordCountsStore", QueryableStoreTypes.keyValueStore()));

		KeyValueIterator<String, Long> range = keyValueStore.all();
		while (range.hasNext()) {
			KeyValue<String, Long> next = range.next();
			System.out.println("Count for " + next.key + ": " + next.value);
		}

		streams.close();
	}
*/
}
