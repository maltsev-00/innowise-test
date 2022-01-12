package com.innowise.test.controller;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<List<UserDto>> getUsers(@Valid @RequestBody UserRequest userRequest) {
        log.debug("Getting users with request: {}", userRequest);
        return userService.findAll(userRequest);
    }

    @PostMapping("/save")
    public Mono<Void> saveUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
        log.debug("Save user with request: {}", userSaveRequest);
        return userService.save(userSaveRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") UUID id) {
        log.debug("Delete user with id: {}", id);
        return userService.delete(id);
    }
}
