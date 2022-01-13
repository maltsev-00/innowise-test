package com.innowise.test.service;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.model.request.UserSearchRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Mono<List<UserDto>> findAll(UserRequest userRequest);

    Mono<UUID> save(UserSaveRequest userSaveRequest);

    Mono<List<UUID>> saveList(List<UserSaveRequest> userSaveRequests);

    Mono<Void> delete(UUID id);

    Mono<List<UserDto>> findUsersByUsername(UserSearchRequest userSearchRequest);
}
