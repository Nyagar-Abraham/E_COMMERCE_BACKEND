package org.abraham.apigateway.service.userservice;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.apigateway.auth.Credentials;
import org.abraham.apigateway.dtos.userservice.*;
import org.abraham.apigateway.mappers.userservice.UserMapper;
import org.abraham.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthServiceGrpc.AuthServiceStub authServiceStub;

    //    ============================================
    //    Method (registerUser)
    //    Handles signup business logic, call the rpc method
    //    ============================================
    public Mono<UserDto> registerUser(RegisterInputDto  requestDto) {
        return Mono.create(sink -> {
            var request = UserMapper.registerInputDtoToRegisterUserRequest(requestDto);
            authServiceStub.registerUser(request, new StreamObserver<User>() {
                @Override
                public void onNext(User response) {
                    sink.success(UserMapper.userToDto(response));
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // No action needed here for unary calls
                }
            });
        });
    }

    //    ============================================
    //    Method (registerUser)
    //    Handles signup business logic, call the rpc method
    //    ============================================
    public Mono<AuthPayloadDto> login(LoginInputDto requestDto) {
        return Mono.create(sink -> {
            var request = UserMapper.loginInputDtoToLoginRequest(requestDto);

            authServiceStub.loginUser(request,new  StreamObserver<LoginResponse>() {
                @Override
                public void onNext(LoginResponse response) {
                    log.info("Received login response, {}", response);
                    sink.success(UserMapper.loginResponseToAuthPayloadDto(response));
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }
                @Override
                public void onCompleted() {

                }
            });
        });
    }

    //    ========================================
    //    mutation (verifyMfaCode)
    //    Handler Method that makes a rpc call to AuthService to verify user MfaCode
    //    =======================================
    public Mono<VerifyMfaCodePayloadDto> verifyMfaCode(VerifyMfaCodeInputDto input, String jwtToken) {
        var request = VerifyMfaCodeRequest.newBuilder().setCode(input.getCode()).build();
        return Mono.create(sink -> {

            authServiceStub
                    .withCallCredentials(new Credentials(jwtToken))
                    .verifyMfaCode(request, new StreamObserver<VerifyMfaCodeResponse>() {
                @Override
                public void onNext(VerifyMfaCodeResponse response) {
                    log.info("MFA Response {}" , response);
                    sink.success(UserMapper.verifyMfaCodeResponseToDto(response));
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }
                @Override
                public void onCompleted() {}
            });
        });
    }

    //    ========================================
    //    mutation (verifyEmail)
    //    Handler Method that makes a rpc call to AuthService to verify user email
    //    =======================================
    public Mono<VerifyEmailResponseDto> verifyEmail(String token) {
        var request = VerifyEmailTokenRequest.newBuilder().setToken(token).build();
        return Mono.create(sink -> {
            authServiceStub.verifyEmailToken(request, new  StreamObserver<VerifyEmailTokenResponse>() {
                @Override
                public void onNext(VerifyEmailTokenResponse response) {
                    log.info("Verify email token response {}" , response);
                    sink.success(UserMapper.verifyEmailResponseToDto(response));
                }
                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }
                @Override
                public void onCompleted() {}
            });
        });
    }

    //    ========================================
    //    mutation (forgotPassword)
    //    Handler Method that makes a rpc call to AuthService to request for password reset
    //    =======================================
    public Mono<ForgotPasswordPayloadDto> forgotPassword(ForgotPasswordInputDto input) {
        var request = ForgotPasswordRequest.newBuilder().setEmail(input.getEmail()).build();

        return Mono.create(sink -> {
            authServiceStub.forgotPassword(request, new StreamObserver<ForgotPasswordResponse>() {
                @Override
                public void onNext(ForgotPasswordResponse response) {
                    sink.success(UserMapper.forgotPasswordResponseToDTO(response));
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {}
            });
        });
    }

    //    ========================================
    //    mutation (resetPassword)
    //    Handler Method that makes a rpc call to AuthService to reset password
    //    =======================================
    public Mono<ResetPasswordPayloadDto> resetPassword(ResetPasswordInputDto input, String token) {
        var request = ResetPasswordRequest.newBuilder()
                .setToken(token)
                .setPassword(input.password())
                .build();

        return Mono.create(sink -> {
            authServiceStub.resetPassword(request, new   StreamObserver<ResetPasswordResponse>() {
                @Override
                public void onNext(ResetPasswordResponse response) {
                    log.info("Reset Password Response {}" , response);
                     sink.success(UserMapper.resetPasswordResponseToDto(response));
                }
                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {}
            });
        });
    }

    //    ========================================
    //    mutation (changePassword)
    //    Handler Method that makes a rpc call to AuthService to change password
    //    =======================================
    public Mono<ChangePasswordPayloadDto> changePassword(ChangePasswordInputDto input, String jwtToken) {
        var request = ChangePasswordRequest.newBuilder()
                .setOldPassword(input.getOldPassword())
                .setNewPassword(input.getNewPassword())
                .build();

        return Mono.create(sink -> {
            authServiceStub
                    .withCallCredentials(new Credentials(jwtToken))
                    .changePassword(request, new StreamObserver<ChangePasswordResponse>() {
                        @Override
                        public void onNext(ChangePasswordResponse response) {
                            log.info("Change Password Response {}" , response);
                            sink.success(UserMapper.changePasswordResponseToDto(response));
                        }

                        @Override
                        public void onError(Throwable t) {
                            sink.error(t);
                        }
                        @Override
                        public void onCompleted() {}
                    });
        });
    }
}
