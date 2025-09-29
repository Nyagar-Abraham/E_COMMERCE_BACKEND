//package org.abraham.user_service.interceptors;
//
//import io.grpc.*;
//import lombok.AllArgsConstructor;
//import org.abraham.constants.Constants;
//import org.abraham.user_service.dto.UserStatus;
//import org.abraham.user_service.handlers.UserHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Component
//@AllArgsConstructor
//public class MfaVerificationInterceptor implements ServerInterceptor {
//    private static final Logger log = LoggerFactory.getLogger(MfaVerificationInterceptor.class);
//    private final UserHandler userHandler;
//
//    @Override
//    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
//        var ctx = Context.current();
//        var isPublic = Constants.IS_PUBLIC.get();
//        log.info("CTX {}, {}", ctx, isPublic);
//
////        If isPublic move to the next.
//        if (Objects.isNull(isPublic)) {
//            return serverCallHandler.startCall(serverCall, metadata);
//        }
//
////  if isPrivate, factor 1 of the authentication is valid
//        var message = serverCall.getMethodDescriptor().getFullMethodName();
//        boolean isVerifyMfaRequest = message.contains(Constants.VERIFY_MFA_METHOD);
//
//        AtomicReference<Boolean> mfaEnabled = new AtomicReference<>(false);
//
//        // Fetch user data synchronously (blocking - not ideal but necessary for interceptor)
//        var userId =UUID.fromString(Constants.USER_ID.get());
//        try {
//            userHandler.findUserById(userId)
//                    .doOnNext(userEntity -> mfaEnabled.set(userEntity.getEnableMfa()))
//                    .block(); // Blocking call - consider async alternatives if possible
//        } catch (Exception e) {
//            log.error("Error fetching user for MFA check", e);
//            close(serverCall,metadata,Status.INTERNAL.withDescription("User verification failed"));
//        }
//
//        ServerCall.Listener<ReqT> delegate = serverCallHandler.startCall(serverCall, metadata);
//
//        // If MFA is not enabled, return the normal listener
//        if (!mfaEnabled.get()) {
//            return delegate;
//        }
//
//
//        // If MFA is enabled, wrap the listener to intercept messages
////        return new MfaServerCallListener<>(delegate, serverCall, metadata);
//
////        return userHandler.findUserById(UUID.fromString(Constants.USER_ID.get()))
////                .flatMap(userEntity -> {
////// If mfa is disabled and
////                    if (!userEntity.getEnableMfa()) return serverCallHandler.startCall(serverCall, metadata);
////// For other methods check if mfa is already enabled
////                    if (!isVerifyMfaRequest )
////                        return userEntity.getStatus().equals(UserStatus.ACTIVE)?
////                                serverCallHandler.startCall(serverCall,metadata) :
////                                close(serverCall, metadata, Status.UNAUTHENTICATED.withDescription("Please Verify MFA Authentication"));
////
////
////// if isVerifyMfa get code from message and verify against secret then set status to active
////                    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serverCall, metadata)) {
////                        @Override
////                        public void onMessage(ReqT message) {
////                            // Access the request message here
////                            log.info("onMessage {}", message);
////
////                            super.onMessage(message);
////                        }
////                    };
////
////
////                });
//
//
////        return
//    }
//
////    ================================
////    CLOSE METHOD
////    ===============================
//    private static <ReqT, RespT> ServerCall.Listener<ReqT> close(ServerCall<ReqT, RespT> serverCall, Metadata metadata, Status status) {
//        serverCall.close(status, metadata);
//        return new ServerCall.Listener<>() {
//        };
//    }
//
//
////    =================================
////    MFA VERIFICATION
////    =================================
//    private static class MfaServerCallListener<ReqT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {
//        private static final Logger log = LoggerFactory.getLogger(MfaServerCallListener.class);
//        private final ServerCall<?, ?> serverCall;
//        private final Metadata metadata;
//
//        protected MfaServerCallListener(ServerCall.Listener<ReqT> delegate, ServerCall<?, ?> serverCall, Metadata metadata) {
//            super(delegate);
//            this.serverCall = serverCall;
//            this.metadata = metadata;
//        }
//
//        @Override
//        public void onMessage(ReqT message) {
//            log.info("MFA enabled - intercepting message: {}", message);
//
//            // Here you can access and process the message when MFA is enabled
//            // Example: Extract MFA token from the message
//            if (isValidMfaRequest(message)) {
//                // If MFA validation passes, continue with the original call
//                super.onMessage(message);
//            } else {
//                // If MFA validation fails, close the call with an error
//                serverCall.close(Status.UNAUTHENTICATED.withDescription("MFA verification required"), metadata);
//            }
//        }
//
//        private boolean isValidMfaRequest(ReqT message) {
//            log.info("Mfa Message, {}", message);
//            // Implement your MFA validation logic here
//            // This could check for MFA token in the message or metadata
//
//            // Example implementation (adapt based on your protobuf structure):
//            try {
//                // Assuming your request messages have a method to get MFA token
//                // You'll need to cast to your specific request type or use reflection
//                log.info("Validating MFA for message: {}", message.getClass().getSimpleName());
//
//                // For now, returning true - implement actual validation
//                // You might want to check if message contains MFA token field
//                return true; // Replace with actual MFA validation
//
//            } catch (Exception e) {
//                log.error("Error validating MFA request", e);
//                return false;
//            }
//        }
//    }
//}
//
//
//
