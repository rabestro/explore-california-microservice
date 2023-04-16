package com.epam.engx.explorecalifornia.controller;

import com.epam.engx.explorecalifornia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signin")
    public Authentication login(@RequestBody @Valid LoginDto loginDto) {
        return userService.signin(loginDto.getUsername(), loginDto.getPassword());
    }
}
