package org.abraham.user_service.config;

import io.grpc.ServerInterceptor;
import org.abraham.user_service.handlers.UserHandler;
import org.abraham.user_service.interceptors.GrpcJwtInterceptor;
import org.abraham.user_service.auth.jwt.JwtUtil;
//import org.abraham.user_service.interceptors.MfaVerificationInterceptor;
import org.abraham.user_service.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;

@Configuration
public class GrpcServerConfig {
    @Bean
    @GlobalServerInterceptor
    @Order(1)
    ServerInterceptor jwtAuthenticationInterceptor(JwtUtil jwtUtil) {
        return new GrpcJwtInterceptor(jwtUtil);
    }

//    @Bean
//    @GlobalServerInterceptor
//    @Order(2)
//    ServerInterceptor MfaVerificationInterceptor(MfaVerificationInterceptor mfaVerificationInterceptor, UserHandler userHandler) {
//        return new MfaVerificationInterceptor(userHandler);
//    }
}
