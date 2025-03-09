package com.ecommerce;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AmazonCloneApplication {

	@Bean
	public ModelMapper modelMapper(){
		// create model mapper instance
		// configure it for transferring matching and not null properties from src to dst object
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
				.setPropertyCondition(Conditions.isNotNull());
		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(AmazonCloneApplication.class, args);
	}

}
