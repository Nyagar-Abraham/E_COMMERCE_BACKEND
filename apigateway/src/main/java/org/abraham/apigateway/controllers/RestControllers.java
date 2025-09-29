package org.abraham.apigateway.controllers;

import org.abraham.apigateway.dtos.userservice.VerifyEmailResponseDto;
import org.abraham.apigateway.service.userservice.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RestControllers {
    private final AuthService authService;

    public RestControllers(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/verify-email")
    public Mono<ResponseEntity<VerifyEmailResponseDto>> verifyEmail(@RequestParam String token) {
        return authService.verifyEmail(token)
                .switchIfEmpty(Mono.just(new VerifyEmailResponseDto(false, "Something went wrong!")))
                .map(response -> ResponseEntity.ok().body(response));
    }
}
