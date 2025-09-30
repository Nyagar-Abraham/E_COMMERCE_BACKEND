package org.abraham.apigateway.datamutaters.userservice;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.abraham.apigateway.dtos.userservice.*;
import org.abraham.apigateway.service.userservice.AuthService;
import org.abraham.constants.Constants;
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

    //    ===========================
    //    mutation (verifyUserMfaCode)
    //    Call the verifyMfaCode handler
    //    ===========================
    @DgsMutation
    public Mono<VerifyMfaCodePayloadDto> verifyUserMfaCode(@InputArgument VerifyMfaCodeInputDto input, DataFetchingEnvironment dfe) {
        String jwtToken = dfe.getGraphQlContext().get(Constants.JWT_TOKEN);
        return authService.verifyMfaCode(input,jwtToken);
    }


    //    ===========================
    //    mutation (forgotPassword)
    //    Call the forgotPassword handler
    //    ===========================
    @DgsMutation
    public Mono<ForgotPasswordPayloadDto> forgotPassword(@InputArgument ForgotPasswordInputDto input ){
        return authService.forgotPassword(input);
    }


    //    ===========================
    //    mutation (resetPassword)
    //    Call the resetPassword handler
    //    ===========================
//    @DgsMutation
//    public Mono<ResetPasswordPayloadDto> resetPassword(@InputArgument RegisterInputDto input ){
//        return authService.resetPassword(input);
//    }
}
