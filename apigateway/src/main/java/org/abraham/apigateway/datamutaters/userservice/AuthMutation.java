package org.abraham.apigateway.datamutaters.userservice;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.abraham.apigateway.dtos.userservice.AuthPayloadDto;
import org.abraham.apigateway.dtos.userservice.LoginInputDto;
import org.abraham.apigateway.dtos.userservice.RegisterInputDto;
import org.abraham.apigateway.dtos.userservice.UserDto;
import org.abraham.apigateway.service.userservice.AuthService;
import reactor.core.publisher.Mono;

@DgsComponent
public class AuthMutation {
    private final AuthService authService;

    public AuthMutation(AuthService authService) {
        this.authService = authService;
    }
    //    ===========================
    //    mutation (register)
    //    signs up new user
    //    ===========================
    @DgsMutation
    public Mono<UserDto> register(@InputArgument RegisterInputDto input) {
       return authService.registerUser(input);
    }

    //    ===========================
    //    mutation (login)
    //    log in a registered user
    //    ===========================
    @DgsMutation
    public Mono<AuthPayloadDto> login(@InputArgument LoginInputDto input) {
        return authService.login(input);
    }
}
