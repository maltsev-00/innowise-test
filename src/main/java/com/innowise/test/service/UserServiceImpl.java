package com.innowise.test.service;

import com.innowise.test.converter.UserConverter;
import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.meta.User_;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CriteriaUserService criteriaUserService;

    @Override
    public Flux<UserDto> getUsers(int pageNo, int pageSize) {
        return Mono.fromCallable(() -> {
                    PageRequest page = PageRequest.of(pageNo, pageSize, Sort.by(User_.EMAIL));
                    return criteriaUserService.findAll(page);
                })
                .flatMapIterable(Slice::getContent)
                .map(userConverter::convertEntityToDto)
                .subscribeOn(Schedulers.boundedElastic());
//                .flatMap(request -> {
//                    PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize());
//                    return Mono.fromCallable(() -> userRepository.findAll(pageRequest));
//                }) //TODO how more correct

    }

    @Override
    public Mono<UUID> saveUser(Mono<UserSaveRequest> userSaveRequest) {
        return userSaveRequest
                .map(userConverter::convertRequestToEntity)
                .flatMap(user -> Mono.fromCallable(() -> userRepository.save(user)))
                .map(User::getId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<UUID> saveUsers(Flux<UserSaveRequest> userSaveRequests) {
        return userSaveRequests
                .map(userConverter::convertRequestToEntity)
                .flatMap(user -> Mono.fromCallable(() -> userRepository.save(user)))
                .map(User::getId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteUser(UUID id) {
        return Mono.fromCallable(() -> {
                    userRepository.deleteById(id);
                    return Mono.empty();
                })
                .then()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<UserDto> getUsers(String email, String username) {
        return Mono.fromCallable(() -> criteriaUserService.findUserByEmailAndByUsername(email, username))
                .flatMapIterable(list -> list)
                .map(userConverter::convertEntityToDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UserDto> findUserById(UUID id) {
        return Mono.fromCallable(() -> criteriaUserService.findUserById(id))
                .map(userConverter::convertEntityToDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
