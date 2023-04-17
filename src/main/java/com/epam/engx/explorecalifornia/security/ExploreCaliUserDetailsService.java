package com.epam.engx.explorecalifornia.security;

import com.epam.engx.explorecalifornia.domain.User;
import com.epam.engx.explorecalifornia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.springframework.security.core.userdetails.User.withUsername;


/**
 * Service to associate user with password and roles setup in the database.
 */
@Component
@RequiredArgsConstructor
public class ExploreCaliUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(() ->
            new UsernameNotFoundException(String.format("User with name %s does not exist", s)));

        //org.springframework.security.core.userdetails.User.withUsername() builder
        return withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRoles())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }
}
