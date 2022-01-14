package com.innowise.test.controller;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.model.request.UserSearchRequest;
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

    @PostMapping//TODO how change to GET
    public Flux<UserDto> getUsers(@Valid @RequestBody Mono<UserRequest> userRequest) {
        return userService.findAll(userRequest)
                .doOnComplete(() -> log.debug("getUsers() success"))
                .doOnError(error -> log.error("getUsers error"));
    }

    @PostMapping("/save")
    public Mono<UUID> save(@Valid @RequestBody Mono<UserSaveRequest> userSaveRequest) {
        return userService.save(userSaveRequest)
                .doOnSuccess(next -> log.debug("saveUser() success"))
                .doOnError(error -> log.error("saveUser() error"));
    }

    @PostMapping("/list")
    public Flux<UUID> saveList(@Valid @RequestBody Flux<UserSaveRequest> userSaveRequests) {
        return userService.saveList(userSaveRequests)
                .doOnComplete(() -> log.debug("saveUsers() success"))
                .doOnError(error -> log.error("saveUsers() error"));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") UUID id) {
        return userService.deleteById(id)
                .doOnSuccess(success -> log.debug("deleteUser() success"))
                .doOnError(error -> log.error("deleteUser() error"));
    }

    @PostMapping("/search")
    public Flux<UserDto> findByEmailAndByUsername(@Valid @RequestBody Mono<UserSearchRequest> userRequest) {
        return userService.findByEmailAndByUsername(userRequest)
                .doOnComplete(() -> log.debug("findUsersByEmailAndByUsername() success"))
                .doOnError(error -> log.error("findUsersByEmailAndByUsername() error"));
    }

    @GetMapping
    public Mono<UserDto> getUser(@RequestParam(name = "id") UUID id) {
        return userService.findById(id)
                .doOnSuccess(success -> log.debug("findUserById() success"))
                .doOnError(error -> log.error("findUserById() error"));
    }
}
