package org.abraham.apigateway.datafetchers;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.abraham.models.AuthServiceGrpc;
import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthDataFetcher {

    private final AuthServiceGrpc.AuthServiceStub authServiceStub;

    public Mono<User> registerUser(){
        return Mono.create(sink -> {
            var request = RegisterUserRequest.newBuilder()
                    .setEmail("manu@gmail.com")
                    .setUsername("manu")
                    .setPassword("password")
                    .setFirstName("Manu")
                    .setLastName("Bahati")
                    .setPhoneNumber("0795873421")
                    .build();

            authServiceStub.registerUser(request, new StreamObserver<User>() {
                @Override
                public void onNext(User response) {
                    sink.success(response);
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
}
