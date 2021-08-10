package com.kafka.user.kafkaConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.kafka.common.model.ProcessLoanDetails;




@Configuration
public class KafkaConsumerConfig {

	@Bean
public ConsumerFactory<String, ProcessLoanDetails> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id-1");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer());
	}
	
	private JsonDeserializer<ProcessLoanDetails> jsonDeserializer() {
		JsonDeserializer<ProcessLoanDetails> deserializer = new JsonDeserializer<>(ProcessLoanDetails.class);
		deserializer.addTrustedPackages("*");
		deserializer.setRemoveTypeHeaders(false);
		return deserializer;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ProcessLoanDetails> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ProcessLoanDetails> factory = 
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
	//	factory.setConcurrency(2);
		return factory;
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		return mapper;
	}
}
