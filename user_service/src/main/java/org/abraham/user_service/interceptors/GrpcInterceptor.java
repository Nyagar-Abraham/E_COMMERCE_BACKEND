package org.abraham.user_service.interceptors;


// GrpcJwtInterceptor.java (simplified)
//public class GrpcJwtInterceptor implements ServerInterceptor {
//
//    private final JwtUtil jwtUtil;
//    private final ReactiveUserDetailsService userDetailsService;
//
//    public GrpcJwtInterceptor(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
//            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
//
//        String token = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Remove "Bearer " prefix
//
//            String finalToken = token;
//            return Context.current().<ServerCall.Listener<ReqT>>with(
//                    ReactiveSecurityContextHolder.CONTEXT_KEY,
//                    userDetailsService.findByUsername(jwtUtil.extractUsername(token))
//                            .flatMap(userDetails -> {
//                                if (jwtUtil.validateToken(finalToken, userDetails)) {
//                                    return Mono.just(new SecurityContextImpl(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())));
//                                }
//                                return Mono.empty();
//                            })
//            ).call(() -> next.startCall(call, headers));
//        }
//        return next.startCall(call, headers);
//    }
//}
