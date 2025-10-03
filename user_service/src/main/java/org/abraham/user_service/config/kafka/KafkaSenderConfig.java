package org.abraham.user_service.config.kafka;


import org.abraham.user_service.dto.kafkamessages.UserCreatedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;


import java.util.HashMap;

import java.util.Map;

@Configuration
public class KafkaSenderConfig {
    @Value(value = "${spring.kafka.bootstrap-servers[0]}")
    private String bootstrapAddress;

    @Bean
    public SenderOptions<String, UserCreatedEvent> userCreatedSenderOptions() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonSerializer.class);
        producerProps.put(JsonSerializer.TYPE_MAPPINGS,
                "usercreatedevent:org.abraham.user_service.dto.kafkamessages.UserCreatedEvent");
        producerProps.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProps.put(ProducerConfig.RETRIES_CONFIG, 3);

        return SenderOptions.create(producerProps);
    }
    @Bean
    public KafkaSender<String, UserCreatedEvent> userCreatedKafkaSender() {
        return KafkaSender.create(userCreatedSenderOptions());
    }
}
