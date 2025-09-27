package org.abraham.apigateway.datafetchers.userservice;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import graphql.schema.DataFetchingEnvironment;
import org.abraham.apigateway.dtos.userservice.UserDto;
import org.abraham.apigateway.service.userservice.UserService;

import org.abraham.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.ContextValue;
import reactor.core.publisher.Mono;

@DgsComponent
public class UserDataFetcher {

    private static final Logger log = LoggerFactory.getLogger(UserDataFetcher.class);
    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    //    ============================================
    //    Method (getCurrentUser)
    //    Gets current Logged in user
    //    ============================================
    @DgsQuery
    public Mono<UserDto> getCurrentUser(DataFetchingEnvironment dfe) {

        var ctx = dfe.getGraphQlContext();
        String jwtToken = ctx.get(Constants.JWT_TOKEN);
        log.info("Jwt header : {}", jwtToken);
        return userService.getCurrentUser(jwtToken);
    }

}
