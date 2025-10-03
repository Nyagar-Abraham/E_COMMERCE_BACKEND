//package org.abraham.product_service.config.kafka;
//
//import org.abraham.product_service.dto.kafkaMessages.UserCreatedEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@Service
//public class UserCreateEventListener {
//
//    private static final Logger log = LoggerFactory.getLogger(UserCreateEventListener.class);
//
//    @KafkaListener(id = "myListener", topics = "user_created_topic")
//    public Mono<Void> listen(String data) {
//        log.debug("Received UserCreatedEvent: {}", data);
//        return Mono.empty();
//    }
//}
