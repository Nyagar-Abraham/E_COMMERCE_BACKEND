package org.abraham.apigateway.service.userservice;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.apigateway.dtos.userservice.AuthPayloadDto;
import org.abraham.apigateway.dtos.userservice.LoginInputDto;
import org.abraham.apigateway.dtos.userservice.RegisterInputDto;
import org.abraham.apigateway.dtos.userservice.UserDto;
import org.abraham.apigateway.mappers.userservice.UserMapper;
import org.abraham.models.AuthServiceGrpc;
import org.abraham.models.LoginResponse;
import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthServiceGrpc.AuthServiceStub authServiceStub;

    //    ============================================
    //    Method (registerUser)
    //    Handles signup business logic
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
    //    Handles signup business logic
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
}
