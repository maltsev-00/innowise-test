package com.innowise.test.controller;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.model.request.UserSearchRequest;
import com.innowise.test.service.LogService;
import com.innowise.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;
    private final LogService logService;

    @PostMapping
    public Mono<List<UserDto>> getUsers(@Valid @RequestBody UserRequest userRequest) {
        log.debug("Getting users with request: {}", userRequest);
        return Mono.just(userRequest)
                .doOnNext(request -> logService.save(request.toString()).subscribe())
                .then(userService.findAll(userRequest));
    }

    @PostMapping("/save-list")
    public Mono<List<UUID>> saveUsers(@Valid @RequestBody List<UserSaveRequest> userSaveRequests) {
        return Mono.just(userSaveRequests)
                .doOnNext(request -> logService.save(Arrays.toString(userSaveRequests.toArray())).subscribe())
                .then(userService.saveList(userSaveRequests));
    }

    @PostMapping("/save")
    public Mono<UUID> saveUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
        log.debug("Save user with request: {}", userSaveRequest);
        return Mono.just(userSaveRequest)
                .doOnNext(request -> logService.save(request.toString()).subscribe())
                .then(userService.save(userSaveRequest));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") UUID id) {
        log.debug("Delete user with id: {}", id);
        return Mono.just(id)
                .doOnNext(request -> logService.save(request.toString()).subscribe())
                .then(userService.delete(id));
    }

    @PostMapping("/search")
    public Mono<List<UserDto>> findUsersByUsername(@Valid @RequestBody UserSearchRequest userRequest) {
        log.debug("Find users by username with request: {}", userRequest.toString());
        return Mono.just(userRequest)
                .doOnNext(request -> logService.save(request.toString()).subscribe())
                .then(userService.findUsersByUsername(userRequest));
    }
}
