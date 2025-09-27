package org.abraham.apigateway.config;

import org.abraham.models.AuthServiceGrpc;
import org.abraham.models.UserServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceChannel {
    @Bean
    public AuthServiceGrpc.AuthServiceStub authServiceStub(GrpcChannelFactory channels) {
        var channel = channels.createChannel("AuthServiceChannel");
        return AuthServiceGrpc.newStub(channel);
    }

    @Bean
    public UserServiceGrpc.UserServiceStub userServiceStub(GrpcChannelFactory channels) {
        var channel = channels.createChannel("UserServiceChannel");
        return UserServiceGrpc.newStub(channel);
    }
}
