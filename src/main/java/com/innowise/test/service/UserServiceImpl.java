package com.innowise.test.service;

import com.innowise.test.converter.UserConverter;
import com.innowise.test.model.dto.UserDto;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public Mono<List<UserDto>> getUsers() {
        return Mono.fromCallable(userRepository::findAll)
                .map(userConverter::convertEntityToDto);
    }
}
