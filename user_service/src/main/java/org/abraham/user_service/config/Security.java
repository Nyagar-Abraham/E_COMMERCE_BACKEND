package org.abraham.user_service.config;

//// SecurityConfig.java (simplified)
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
//                                                         ReactiveAuthenticationManager authenticationManager,
//                                                         ServerSecurityContextRepository securityContextRepository) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                .authenticationManager(authenticationManager)
//                .securityContextRepository(securityContextRepository)
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/public/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .addFilterAt(new AuthenticationWebFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
//                .build();
//    }
//
//    @Bean
//    public ReactiveAuthenticationManager authenticationManager(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
//        return authentication -> {
//            String token = authentication.getCredentials().toString();
//            String username = jwtUtil.extractUsername(token);
//            return userDetailsService.findByUsername(username)
//                    .flatMap(userDetails -> {
//                        if (jwtUtil.validateToken(token, userDetails)) {
//                            return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
//                        }
//                        return Mono.empty();
//                    });
//        };
//    }
//
//    @Bean
//    public ServerSecurityContextRepository securityContextRepository() {
//        return new ServerSecurityContextRepository() {
//            @Override
//            public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
//                // ... save context if needed
//                return Mono.empty();
//            }
//
//            @Override
//            public Mono<SecurityContext> load(ServerWebExchange exchange) {
//                // ... load context from exchange (e.g., from JWT in headers)
//                return Mono.empty();
//            }
//        };
//    }
//}
