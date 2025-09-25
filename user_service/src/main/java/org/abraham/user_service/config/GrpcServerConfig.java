package org.abraham.user_service.config;

import io.grpc.ServerInterceptor;
import org.abraham.user_service.interceptors.GrpcJwtInterceptor;
import org.abraham.user_service.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.GlobalServerInterceptor;

@Configuration
public class GrpcServerConfig {
    @Bean
    @GlobalServerInterceptor
    ServerInterceptor globalServerInterceptor(JwtUtil jwtUtil) {
        return new GrpcJwtInterceptor(jwtUtil);
    }
}
