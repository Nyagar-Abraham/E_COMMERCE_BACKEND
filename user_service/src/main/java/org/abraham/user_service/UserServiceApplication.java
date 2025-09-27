package org.abraham.user_service;

import lombok.extern.slf4j.Slf4j;
import org.abraham.user_service.auth.jwt.JwtProperties;
import org.abraham.user_service.repository.AddressRepository;
import org.abraham.user_service.repository.UserPreferenceRepository;
import org.abraham.user_service.repository.UserProfileRepository;
import org.abraham.user_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class UserServiceApplication {

	public static void main(String[] args) {

     var context =   SpringApplication.run(UserServiceApplication.class, args);
//        var user = UserEntity.builder()
//                .id(UUID.randomUUID())
//                .username("username")
//                .email("email@gmail.com")
//                .role(UserRoles.ADMIN)
//                .phoneNumber("0746537543")
//                .build();
//        var accessToken =  context.getBean(JwtUtil.class).generateAccessToken(user);
//        var refreshToken =  context.getBean(JwtUtil.class).generateAccessToken(user);
//        log.info("accessToken: {}", accessToken);
//        log.info("refreshtoken: {}", refreshToken);

	}

    @Bean
    public CommandLineRunner init(UserRepository userRepository, UserProfileRepository userProfileRepository, UserPreferenceRepository userPreferenceRepository, AddressRepository addressRepository) {
        return args -> {













//            var preference = new UserPreference();
//            preference.setCurrency("KSH");
//            preference.setCreatedAt(LocalDateTime.now());
//            preference.setUpdatedAt(LocalDateTime.now());
//
//            var address = new Address();
//            address.setUserId(user.getId());
//            address.setAddressLine1("line_1");
//            address.setAddressLine2("line_2");
//            address.setCity("city");
//            address.setState("state");
//            address.setPostalCode("postal_code");
//            address.setCountry("country");
//            address.setCreatedAt(LocalDateTime.now());
//            address.setUpdatedAt(LocalDateTime.now());

//            UUID id = UUID.fromString("bd4c5234-016e-40b3-9089-9baa24f7ea2e");
//
//            userRepository.findById(id)
//                    .flatMap(u -> userProfileRepository.findByUserId(u.getId())
//                            .map(p -> {
//                                  u.setProfile(p);
//                                  return u;
//                            })
//
//                    )
//                    .doOnNext(u -> log.info("User with profile: {}",u))
//                    .block();


//            userRepository.save(user).
//                    doOnNext(user1 -> {
//                        var profile = new UserProfile();
//                        profile.setAvatar("https://avatars.githubusercontent.com/u/4324?v=4");
//                        profile.setCreatedAt(LocalDateTime.now());
//                        profile.setUpdatedAt(LocalDateTime.now());
//                        profile.setUserId(user1.getId());
//                        userProfileRepository.save(profile)
//                                .doOnNext(p -> log.info("profile:{}",p))
//                                .publishOn(Schedulers.boundedElastic())
//                                .subscribe();
//                    })
//                    .doOnError(System.err::println)
//                    .block();


//            user_id        UUID                     NOT NULL,
//            type           address_type_enum        NOT NULL DEFAULT 'BOTH',
//                    is_default     BOOLEAN                  NOT NULL DEFAULT FALSE,
//                    address_line_1 VARCHAR(255)             NOT NULL,
//            address_line_2 VARCHAR(255),
//                    city           VARCHAR(100)             NOT NULL,
//            state          VARCHAR(100)             NOT NULL,
//            postal_code    VARCHAR(20)              NOT NULL,
//            country        VARCHAR(2)               NOT NULL, -- ISO 3166-1 alpha-2 country code
//            created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
//                    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP


        };
    }
}


//(
//id                            UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
//email                         VARCHAR(255)     NOT NULL UNIQUE,
//username                      VARCHAR(50) UNIQUE,
//password_hash                 VARCHAR(255)     NOT NULL,
//first_name                    VARCHAR(100),
//last_name                     VARCHAR(100),
//phone_number                  VARCHAR(20),
//status                        user_status_enum NOT NULL DEFAULT 'PENDING_VERIFICATION',
//email_verified                BOOLEAN          NOT NULL DEFAULT FALSE,
//phone_verified                BOOLEAN          NOT NULL DEFAULT FALSE,
//email_verification_token      VARCHAR(255),
//email_verification_expires_at TIMESTAMPTZ,
//phone_verification_token      VARCHAR(10),
//phone_verification_expires_at TIMESTAMPTZ,
//password_reset_token          VARCHAR(255),
//password_reset_expires_at     TIMESTAMPTZ,
//last_login_at                 TIMESTAMPTZ,
//created_at                    TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP,
//updated_at                    TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP
//);