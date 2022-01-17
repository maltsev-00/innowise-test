package com.innowise.test.controller;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserDto> getUsers(@Valid Mono<UserRequest> userRequest) {
        return userService.getUsers(userRequest)
                .doOnComplete(() -> log.debug("getUsers() success"))
                .doOnError(error -> log.error("getUsers error"));
    }

    @PostMapping
    public Mono<UUID> saveUser(@Valid @RequestBody Mono<UserSaveRequest> userSaveRequest) {
        return userService.saveUser(userSaveRequest)
                .doOnSuccess(success -> log.debug("saveUser() success"))
                .doOnError(error -> log.error("saveUser() error"));
    }

    @DeleteMapping("/{idUser}")
    public Mono<Void> deleteUser(@PathVariable("idUser") UUID id) {
        return userService.deleteUser(id)
                .doOnSuccess(success -> log.debug("deleteUser() success"))
                .doOnError(error -> log.error("deleteUser() error"));
    }

    @PostMapping("/list")
    public Flux<UUID> saveUsers(@Valid @RequestBody Flux<UserSaveRequest> userSaveRequests) {
        return userService.saveUsers(userSaveRequests)
                .doOnComplete(() -> log.debug("saveUsers() success"))
                .doOnError(error -> log.error("saveUsers() error"));
    }

}