package org.abraham.product_service;

import org.abraham.product_service.dto.kafkaMessages.UserCreatedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;


@SpringBootApplication
public class ProductServiceApplication {


    public static void main(String[] args) {
      var context =  SpringApplication.run(ProductServiceApplication.class, args);
      ReceiverOptions<String, UserCreatedEvent> options =   context.getBean(ReceiverOptions.class);

        KafkaReceiver.create(options)
                .receive()
                .subscribe(r -> {
                    System.out.printf("Received message: %s\n", r);
                    r.receiverOffset().acknowledge();
                });
    }

}
