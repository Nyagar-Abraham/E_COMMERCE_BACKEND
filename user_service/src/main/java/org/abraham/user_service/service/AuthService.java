package org.abraham.user_service.service;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.abraham.models.LoginRequest;
import org.abraham.models.LoginResponse;
import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.abraham.user_service.handlers.AuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

import java.time.Instant;

@GrpcService
public class AuthService extends org.abraham.models.AuthServiceGrpc.AuthServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthHandler authHandler;

    public AuthService(AuthHandler authHandler) {
        super();
        this.authHandler = authHandler;
    }

    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<User> responseObserver) {
        authHandler
                .registerUser(Mono.just(request))
                .doOnNext(user -> {
                    responseObserver.onNext(user);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> log.error("Error while registering user: {}", err.getMessage()))
                .subscribe();
    }

    @Override
    public void loginUser(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        super.loginUser(request, responseObserver);
    }
}
