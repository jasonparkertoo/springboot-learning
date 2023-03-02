package com.example.kafka.service;

import com.example.kafka.service.config.KafkaConfigProps;
import com.example.kafka.service.domain.CustomerVisitEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootApplication
public class KafkaServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(KafkaServiceApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(final KafkaTemplate<String, String> kafkaTemplate,
                                    final KafkaConfigProps kafkaConfigProps,
                                    final ObjectMapper objectMapper) throws JsonProcessingException
    {
        final var event = CustomerVisitEvent.builder()
                            .customerId(UUID.randomUUID().toString())
                            .dateTime(LocalDateTime.now())
                            .build();

        final var payload = objectMapper.writeValueAsString(event);
        return args -> {
            kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
        };
    }

    @KafkaListener(topics = "customer.visit")
    public String listens(final String in)
    {
        log.info("Heard: {}", in);
        return in;
    }
}
