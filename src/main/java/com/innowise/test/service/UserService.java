package com.innowise.test.service;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Mono<List<UserDto>> findAll(UserRequest userRequest);

    Mono<Void> save(UserSaveRequest userSaveRequest);

    Mono<Void> delete(UUID id);
}
