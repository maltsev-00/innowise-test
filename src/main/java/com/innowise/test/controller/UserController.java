package com.innowise.test.controller;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    // TODO Method save nothing but you marked as POST. Follow http contract and user GET
    // TODO Why Mono in the request?
    @GetMapping
    public Flux<UserDto> getUsers(@NotNull @RequestParam(name = "page", defaultValue = "0") int pageNo,
                                  @NotNull @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        return userService.getUsers(pageNo, pageSize)
                .doOnComplete(() -> log.debug("getUsers() success"))
                .doOnError(error -> log.error("getUsers error"));
    }

    // TODO Delete /save, stay only @PostMapping because POST means save
    @PostMapping
    public Mono<UUID> saveUser(@Valid @RequestBody Mono<UserSaveRequest> userSaveRequest) {
        return userService.saveUser(userSaveRequest)
                .doOnSuccess(success -> log.debug("saveUser() success"))
                .doOnError(error -> log.error("saveUser() error"));
    }

    @DeleteMapping("/{idUser}")
    // TODO Better to rename deleteById- deleteUser to make clen API. From the name of parameter you can understand that you can delete user by id.
    public Mono<Void> deleteUser(@PathVariable("idUser") UUID id) {
        return userService.deleteUser(id)
                .doOnSuccess(success -> log.debug("deleteUser() success"))
                .doOnError(error -> log.error("deleteUser() error"));
    }

    // TODO I think you can use the same name for save. It will be overloading
    // TODO Better to rename saveList -> saveUsers to make clen API
    @PostMapping("/list")
    public Flux<UUID> saveUsers(@Valid @RequestBody Flux<UserSaveRequest> userSaveRequests) {
        return userService.saveUsers(userSaveRequests)
                .doOnComplete(() -> log.debug("saveUsers() success"))
                .doOnError(error -> log.error("saveUsers() error"));
    }

    @GetMapping("search")
    // TODO rename to getUsers. Method in line 30 is no needed because you can get all by this method just call without parameters
    public Flux<UserDto> findByEmailAndByUsername(@NotNull @RequestParam(name = "email") String email,
                                                  @NotNull @RequestParam(name = "username") String username) {
        return userService.getUsers(email, username)
                .doOnComplete(() -> log.debug("findByEmailAndByUsername() success"))
                .doOnError(error -> log.error("findByEmailAndByUsername() error"));
    }

    @GetMapping("{idUser}")
    //TODO Implement similar method as getUsers by specification. Method should return single usr. In the services you can use the same specification
    public Mono<UserDto> findUserById(@PathVariable(name = "idUser") UUID id) {
        return userService.findUserById(id)
                .doOnSuccess(success -> log.debug("findUserById() success"))
                .doOnError(error -> log.error("findUserById() error"));
    }
}