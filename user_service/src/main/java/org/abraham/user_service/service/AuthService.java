package org.abraham.user_service.service;


import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.abraham.constants.Constants;
import org.abraham.models.*;
import org.abraham.user_service.auth.jwt.JwtUtil;
import org.abraham.user_service.auth.mfa.TotpManagerImpl;
import org.abraham.user_service.handlers.AuthHandler;
import org.abraham.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.UUID;

@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthHandler authHandler;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TotpManagerImpl totpManagerImpl;

    public AuthService(AuthHandler authHandler, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, TotpManagerImpl totpManagerImpl) {
        super();
        this.authHandler = authHandler;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.totpManagerImpl = totpManagerImpl;
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

    //    =================================
//    VERIFY USER EMAIL
//    =================================
    @Override
    public void verifyEmailToken(VerifyEmailTokenRequest request, StreamObserver<VerifyEmailTokenResponse> responseObserver) {
        log.info("Received VerifyEmailTokenRequest {}", request);
        authHandler.verifyEmailToken(request.getToken()).subscribe();
    }

    //    =====================================
    //    LOGIN USER METHOD (registerUser)
    //    =====================================
    @Override
    public void loginUser(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        log.info("Received login request: {}", request);

//        Authenticate user
        var user = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
//        TODO HANDLE ERROR

        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Id, {}", Constants.USER_ID.get());

        authHandler.login(request)
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> {
                    log.error("Error while logging user in: {}", err.getMessage());
                    responseObserver.onError(Status.INTERNAL
                            .withDescription("Login failed: " + err.getMessage())
                            .asRuntimeException());
                })
                .subscribe();

    }

    //    =================================
//    VERIFY MFA
//    =================================
    @Override
    public void verifyMfaCode(VerifyMfaCodeRequest request, StreamObserver<VerifyMfaCodeResponse> responseObserver) {
        var code = request.getCode();
        var UserId = UUID.fromString(Constants.USER_ID.get());
        authHandler.verifyMfaCode(code, UserId)
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> {
                    log.error("Error while verifying mfa code: {}", err.getMessage());
                    responseObserver.onError(Status.INTERNAL
                            .withDescription("Mfa Verification failed" + err.getMessage())
                            .asRuntimeException());
                })
                .subscribe();
    }


    //    =================================
//    FORGOT PASSWORD
//    =================================
    @Override
    public void forgotPassword(ForgotPasswordRequest request, StreamObserver<ForgotPasswordResponse> responseObserver) {
        authHandler.forgotPassword(request)
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> {
                    log.error("Error while forgotting password: {}", err.getMessage());
                    responseObserver.onError(Status.INTERNAL
                            .withDescription("Password Reset Request Failed" + err.getMessage())
                            .asRuntimeException());
                })
                .subscribe();
    }


    //    =================================
//    RESET PASSWORD
//    =================================
    @Override
    public void resetPassword(ResetPasswordRequest request, StreamObserver<ResetPasswordResponse> responseObserver) {
        authHandler.resetPassword(request)
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> {
                    log.error("Error while resetting password: {}", err.getMessage());
                    responseObserver.onError(Status.INTERNAL
                            .withDescription("Password Reset Failed: " + err.getMessage())
                            .asRuntimeException());
                })
                .subscribe();
    }

    @Override
    public void changePassword(ChangePasswordRequest request, StreamObserver<ChangePasswordResponse> responseObserver) {
        var userId = UUID.fromString(Constants.USER_ID.get());
        authHandler.changePassword(request, userId)
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .doOnError(err -> {
                    log.error("Error while changing password: {}", err.getMessage());
                    responseObserver.onError(Status.INTERNAL
                            .withDescription("Password Reset Failed: " + err.getMessage())
                            .asRuntimeException());
                })
                .subscribe();

    }
}
