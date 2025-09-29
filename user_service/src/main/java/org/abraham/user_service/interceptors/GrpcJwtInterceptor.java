package org.abraham.user_service.interceptors;


import io.grpc.*;
import lombok.AllArgsConstructor;
import org.abraham.constants.Constants;
import org.abraham.user_service.auth.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@AllArgsConstructor
public class GrpcJwtInterceptor implements ServerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(GrpcJwtInterceptor.class);
    private final JwtUtil jwtUtil;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        var message = serverCall.getMethodDescriptor().getFullMethodName();

        log.info("Server received request for method {}", message);

        boolean isPublic = Constants.PUBLIC_METHODS.stream()
                .anyMatch(message::contains);

        if (isPublic) {
            var ctx = Context.current();
                    ctx.withValue(Constants.IS_PUBLIC,"true");
           return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
        }

        log.info("{} , not filtered",message);


//        GET AUTHORIZATION HEADER & TOKEN
        var authHeader = metadata.get(Constants.AUTHORIZATION_KEY);
        log.info("Authorization header is {}", authHeader);
        var jwtToken = extractToken(authHeader);
        if (Objects.isNull(jwtToken)) {
           return close(serverCall,metadata,Status.UNAUTHENTICATED.withDescription("Missing Authorization Header"));
        }

        log.info("Token {}",  jwtToken);
//        PARSE THE TOKEN
        var jwt = jwtUtil.parseToken(jwtToken.trim());


        if (Objects.isNull(jwt) || jwt.isExpired()) {
           return close(serverCall,metadata,Status.UNAUTHENTICATED.withDescription("JWT Token is expired or invalid"));
        }

//        Authenticate User
//        ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(jwt.getEmail(),"" , Collections.singletonList(new SimpleGrantedAuthority(jwt.getRole().toString()))));


//        If MFA is not enabled proc
//        SET GRPC CONTEXT
        var ctx = Context.current()
                .withValue(Constants.USER_ID, jwt.getUserId())
                .withValue(Constants.USER_ROLE,jwt.getRole().toString())
                .withValue(Constants.USER_EMAIL,jwt.getEmail());

        return Contexts.interceptCall(ctx,serverCall,metadata,serverCallHandler);
    }

    private String extractToken(String authHeader) {
        return Objects.nonNull(authHeader) && authHeader.startsWith(Constants.BEARER) ? authHeader.substring(Constants.BEARER.length()) : null;
    }

    private static <ReqT, RespT>  ServerCall.Listener<ReqT> close(ServerCall<ReqT, RespT> serverCall, Metadata metadata, Status status) {
        serverCall.close(status, metadata);
        return new ServerCall.Listener<>() {};
    }

}
