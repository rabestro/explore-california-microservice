package com.epam.engx.explorecalifornia.service;

import com.epam.engx.explorecalifornia.repository.RoleRepository;
import com.epam.engx.explorecalifornia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Authentication signin(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
