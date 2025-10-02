package org.abraham.user_service.config.kafka;

import org.abraham.constants.Constants;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

//    @Value(value = "${spring.kafka.bootstrap-servers")
//    private String bootstrapAddress;

//    @Autowired
//    private KafkaAdmin admin;



    @Bean
    public NewTopic topic5() {
        return TopicBuilder.name(Constants.USER_CREATED_TOPIC)
                .replicas(1)
                .build();
    }
}
