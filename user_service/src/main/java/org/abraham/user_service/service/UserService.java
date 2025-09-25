package org.abraham.user_service.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.abraham.constants.Constants;
import org.abraham.models.User;
import org.abraham.models.UserServiceGrpc;
import org.abraham.user_service.mapper.UserMapper;
import org.abraham.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public void getCurrentUser(Empty request, StreamObserver<User> responseObserver) {
        log.info("getCurrentUser");
        log.info("Context User Id, {}", Constants.USER_ID.get());
        log.info("Context User Role, {}", Constants.USER_EMAIL.get());
        log.info("Context User Role, {}", Constants.USER_ROLE.get());

        var userId = Constants.USER_ID.get();
        userRepository.findById(UUID.fromString(userId))
                .map(UserMapper::userEntityToUser)
                .doOnNext(user -> {
                    responseObserver.onNext(user);
                    responseObserver.onCompleted();
                })
                .subscribe();

    }
}
