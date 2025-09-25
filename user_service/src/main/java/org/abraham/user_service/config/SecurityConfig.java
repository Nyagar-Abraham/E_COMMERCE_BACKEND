package org.abraham.user_service.config;

import lombok.AllArgsConstructor;
import org.abraham.user_service.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.grpc.server.security.AuthenticationProcessInterceptor;
import org.springframework.grpc.server.security.GrpcSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailService userService;

    @Bean
    AuthenticationManager authenticationManager() {
        var authProvider = new DaoAuthenticationProvider(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    @GlobalServerInterceptor
    AuthenticationProcessInterceptor jwtSecurityFilterChain(GrpcSecurity grpc) throws Exception {
        return grpc
                .authorizeRequests(requests -> requests
//                        .methods("UserService/*").authenticated()
                        .methods("AuthService/*").permitAll()
                        .methods("grpc.*/*").permitAll()
                        .allRequests().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .preauth(Customizer.withDefaults())
                .authenticationManager(authenticationManager())
                .build();
    }
}
