package org.abraham.product_service.config.kafka;


import org.abraham.constants.Constants;
import org.abraham.product_service.dto.kafkaMessages.UserCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaReceiverConfig {
    @Value("${spring.kafka.bootstrap-servers[0]}")
    private String bootstrapServers;


    @Bean
    public ReceiverOptions<String, UserCreatedEvent> userCreatedEventReceiverOptions() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "product-service-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(JsonDeserializer.TYPE_MAPPINGS, "usercreatedevent:org.abraham.product_service.dto.kafkaMessages.UserCreatedEvent");


        return ReceiverOptions.<String, UserCreatedEvent>create(consumerProps)
                .subscription(Collections.singleton(Constants.USER_CREATED_TOPIC));
    }
}
