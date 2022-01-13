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
    public Mono<UUID> saveUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
        log.debug("Save user with request: {}", userSaveRequest);
        return userService.save(userSaveRequest);
    }

    @PostMapping("/save-list")
    public Mono<List<UUID>> saveUsers(@Valid @RequestBody List<UserSaveRequest> userSaveRequests) {
        return userService.saveList(userSaveRequests);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") UUID id) {
        log.debug("Delete user with id: {}", id);
        return userService.delete(id);
    }

    @PostMapping("/search")
    public Mono<List<UserDto>> findUsersByUsername(@Valid @RequestBody UserSearchRequest userRequest) {
        log.debug("Find users by username with request: {}", userRequest.toString());
        return userService.findUsersByUsernameAndSort(userRequest);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable("id") UUID id) {
        log.debug("Find user by id: {}", id);
        return userService.findUserById(id);
    }
}
