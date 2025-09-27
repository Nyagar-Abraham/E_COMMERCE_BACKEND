package org.abraham.user_service.interceptors;

import io.grpc.*;
import org.abraham.constants.Constants;
import org.abraham.user_service.handlers.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class MfaVerificationInterceptor implements ServerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MfaVerificationInterceptor.class);
    private final UserHandler userHandler;

    public MfaVerificationInterceptor(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        var ctx = Context.current();
        var userId = Constants.USER_ID.get();
        log.info("CTX {}, {}", ctx, userId);
        if (Objects.isNull(userId)) {
            return serverCallHandler.startCall(serverCall, metadata);
        }

        return userHandler.findUserById(UUID.fromString(userId))
                .flatMap(userEntity -> {
                    if (!userEntity.getEnableMfa()) return serverCallHandler.startCall(serverCall, metadata);
// if MFA is Enabled
                    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serverCall, metadata)) {
                        @Override
                        public void onMessage(ReqT message) {
                            // Access the request message here
                            log.info("onMessage {}", message);

                            super.onMessage(message);
                        }
                    };
                });


//        return
    }
}
