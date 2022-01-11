package com.innowise.test.service;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<List<UserDto>> getUsers();
}
