package com.innowise.test.controller;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Mono<List<UserDto>> getUsers() {
        return userService.getUsers();
    }
}
