package com.innowise.test.service;

import com.innowise.test.converter.UserConverter;
import com.innowise.test.exception.PaginationException;
import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public Mono<List<UserDto>> findAll(UserRequest userRequest) {
        return Mono.fromCallable(() -> {
                    try {
                        Pageable pageable = PageRequest.of(userRequest.getPageNo(), userRequest.getPageSize());
                        return userRepository.findAll(pageable);
                    } catch (Exception exception) {
                        throw new PaginationException(exception.getMessage());
                    }
                })
                .map(page -> userConverter.convertEntityToDto(page.getContent()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> save(UserSaveRequest userSaveRequest) {
        return Mono.just(userSaveRequest)
                .map(userRequest -> {
                    User user = userConverter.convertRequestToEntity(userRequest);
                    return userRepository.save(user);
                }).then()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return Mono.fromCallable(() -> {
                    userRepository.deleteById(id);
                    return Mono.empty();
                })
                .then()
                .subscribeOn(Schedulers.boundedElastic());
    }
}
