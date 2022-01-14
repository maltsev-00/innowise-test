package com.innowise.test.service;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.model.request.UserSearchRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Flux<UserDto> findAll(Mono<UserRequest> userRequestMono);

    Mono<UUID> save(Mono<UserSaveRequest> userSaveRequest);

    Flux<UUID> saveList(Flux<UserSaveRequest> userSaveRequests);

    Mono<Void> deleteById(UUID id);

    Flux<UserDto> findByEmailAndByUsername(Mono<UserSearchRequest> userSearchRequest);

    Mono<UserDto> findById(UUID id);
}
