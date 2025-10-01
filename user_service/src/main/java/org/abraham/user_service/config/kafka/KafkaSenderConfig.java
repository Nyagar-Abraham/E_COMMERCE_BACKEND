package org.abraham.user_service.config.kafka;

import org.abraham.messages.UserCreatedMessage;
import org.abraham.user_service.config.kafka.serializers.UserCreatedMessageSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaSenderConfig {
    @Value(value = "${spring.kafka.bootstrap-servers[0]}")
    private String bootstrapAddress;

    @Bean
    public <K,V>  SenderOptions<K,V> senderOptions() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserCreatedMessageSerializer.class);

        return SenderOptions.<K , V>create(producerProps);
    }

    @Bean
    public KafkaSender<String, UserCreatedMessage> kafkaSender() {
        return KafkaSender.create(senderOptions());
    }

}
