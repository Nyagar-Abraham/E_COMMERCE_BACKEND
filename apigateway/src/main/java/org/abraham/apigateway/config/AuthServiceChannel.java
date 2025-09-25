package org.abraham.apigateway.config;

import org.abraham.models.AuthServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceChannel {
    @Bean
    public AuthServiceGrpc.AuthServiceStub authServiceStub(GrpcChannelFactory channels) {
        var channel = channels.createChannel("AuthChannel");
        return AuthServiceGrpc.newStub(channel);
    }
}
