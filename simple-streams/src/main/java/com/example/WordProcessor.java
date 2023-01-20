package com.example;

import java.util.Arrays;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.ValueMapper;
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
	void buildPipeline(StreamsBuilder streamsBuilder) {
		KStream<String, String> messageStream = streamsBuilder.stream(topic,
				Consumed.with(STRING_SERDE, STRING_SERDE));

		KTable<String, Long> wordCounts = messageStream
				.mapValues((ValueMapper<String, String>) String::toLowerCase)
				.flatMapValues(value -> Arrays.asList(value.split("\\W+")))
				.groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
				.count(Materialized.as("counts"));

		wordCounts.toStream().to("logtopic");
	}

}
