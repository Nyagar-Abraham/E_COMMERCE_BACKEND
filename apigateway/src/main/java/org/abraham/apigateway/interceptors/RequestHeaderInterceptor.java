package org.abraham.apigateway.interceptors;

import org.abraham.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.Collections;

@Component
public class RequestHeaderInterceptor implements WebGraphQlInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestHeaderInterceptor.class);

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("GraphqlRequest: {}", request.getUri());
        var authorizationHeader = request.getHeaders().getFirst(Constants.AUTHORIZATION_HEADER);

        log.info("Authorization header: {}", authorizationHeader);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
         return chain.next(request);
        }
        var jwtToken = authorizationHeader.substring(Constants.BEARER.length());

        request.configureExecutionInput((executionInput, builder) ->
                builder.graphQLContext(Collections.singletonMap(Constants.JWT_TOKEN, jwtToken)).build()
        );

        return chain.next(request);
    }
}
