package org.abraham.apigateway.service.userservice;

import com.google.protobuf.Empty;
import com.netflix.graphql.dgs.DgsComponent;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.abraham.apigateway.auth.Credentials;
import org.abraham.apigateway.dtos.userservice.LoginInputDto;
import org.abraham.apigateway.dtos.userservice.UserDto;
import org.abraham.apigateway.mappers.userservice.UserMapper;
import org.abraham.models.User;
import org.abraham.models.UserServiceGrpc;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {
    private final UserServiceGrpc.UserServiceStub userServiceStub;
    //    ============================================
    //    Method (getCurrentUser)
    //    Gets Current logged In User
    //    ============================================
    public Mono<UserDto> getCurrentUser(String jwtToken) {
        return Mono.create(monoSink -> {
            userServiceStub
                    .withCallCredentials(new Credentials(jwtToken))
                    .getCurrentUser(Empty.getDefaultInstance(), new StreamObserver<User>() {
                @Override
                public void onNext(User user) {
                    monoSink.success(UserMapper.userToDto(user));
                }

                @Override
                public void onError(Throwable throwable) {
                 monoSink.error(throwable);
                }

                @Override
                public void onCompleted() {

                }
            });
        });
    }
}
