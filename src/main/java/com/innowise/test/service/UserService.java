package com.innowise.test.service;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Flux<UserDto> getUsers(Mono<UserRequest> userRequest);

    Mono<UUID> saveUser(Mono<UserSaveRequest> userSaveRequest);

    Flux<UUID> saveUsers(Flux<UserSaveRequest> userSaveRequests);

    Mono<Void> deleteUser(UUID id);
}
