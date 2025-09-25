package org.abraham.user_service.service;


import io.grpc.stub.StreamObserver;
import org.abraham.constants.Constants;
import org.abraham.models.*;
import org.abraham.user_service.handlers.AuthHandler;
import org.abraham.user_service.jwt.JwtUtil;
import org.abraham.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthHandler authHandler;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthService(AuthHandler authHandler, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        super();
        this.authHandler = authHandler;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    //    =====================================
    //    REGISTER USER METHOD (registerUser)
    //    =====================================
    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<User> responseObserver) {

        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Id, {}", Constants.USER_ID.get());
        authHandler
                .registerUser(Mono.just(request))
                .doOnNext(user -> {
                    responseObserver.onNext(user);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> log.error("Error while registering user: {}", err.getMessage()))
                .subscribe();
    }

    //    =====================================
    //    REGISTER USER METHOD (registerUser)
    //    =====================================
    @Override
    public void loginUser(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        log.info("Received login request: {}", request);

        var user = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Id, {}", Constants.USER_ID.get());

        log.info("Email, {}", request.getEmail());
        userRepository.findByEmail(request.getEmail())
                .flatMap(u -> userRepository.updateLastLoginAt(LocalDateTime.now(ZoneId.systemDefault()), u.getId()).map(i -> u))
                .doOnNext(u -> {
                    log.info("Run: {}", u);
                    var accessToken = jwtUtil.generateAccessToken(u);
                    log.debug("Generated access token: {}", accessToken);
                    var refreshToken = jwtUtil.generateRefreshToken(u);


                    log.debug("Generated refresh token: {}", refreshToken);

                    var response = LoginResponse.newBuilder()
                            .setAccessToken(accessToken)
                            .setRefreshToken(refreshToken)
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> log.error("Error while logging user in: {}", err.getMessage()))
                .subscribe();
    }
}
