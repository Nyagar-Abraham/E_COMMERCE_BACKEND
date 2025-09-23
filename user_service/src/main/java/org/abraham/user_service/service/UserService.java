package org.abraham.user_service.service;

import lombok.AllArgsConstructor;
import org.abraham.user_service.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return userRepository.findByEmail(email)
                .map(user -> {
                    var role = user.getRole();
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
                    return new User(user.getEmail(), user.getPasswordHash(), authorities);
                })
                .publishOn(Schedulers.boundedElastic())
                .block();
    }
}
