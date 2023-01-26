package com.example.stream;

import java.util.Arrays;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WordProcessor {

	private static final Serde<String> STRING_SERDE = Serdes.String();
	
	@Value("${test.logtopic}")
    private String logtopic;
	
	@Value("${test.topic}")
    private String topic;

	@Autowired
	public void buildPipeline(StreamsBuilder streamsBuilder) {
		KStream<String, String> messageStream = streamsBuilder.stream(topic,
				Consumed.with(STRING_SERDE, STRING_SERDE));

		KTable<String, Long> wordCounts = messageStream
				.mapValues((ValueMapper<String, String>) String::toLowerCase)
				.flatMapValues(value -> Arrays.asList(value.split("\\W+")))
				.groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
				//.count(Materialized.as("counts")
				.count(Materialized.as(Stores.inMemoryKeyValueStore("counts"))

		);

		wordCounts.toStream().to(logtopic);

		wordCounts.toStream().print(Printed.toSysOut());

	}

}
