package com.innowise.test.service;

import com.innowise.test.converter.UserConverter;
import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.meta.User_;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.model.request.UserSearchRequest;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Flux<UserDto> findAll(Mono<UserRequest> userRequest) {
        return userRequest
//                .flatMap(request -> {
//                    PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize());
//                    return Mono.fromCallable(() -> userRepository.findAll(pageRequest));
//                }) //TODO how more correct
                .map(request -> PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(User_.EMAIL)))
                .flatMap(pageRequest -> Mono.fromCallable(() -> criteriaUserService.findAll(pageRequest)))
                .flatMapIterable(Slice::getContent)
                .map(userConverter::convertEntityToDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UUID> save(Mono<UserSaveRequest> userSaveRequest) {
        return userSaveRequest
                .map(userConverter::convertRequestToEntity)
                .flatMap(user -> Mono.fromCallable(() -> userRepository.save(user)))
                .map(User::getId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<UUID> saveList(Flux<UserSaveRequest> userSaveRequests) {
        return userSaveRequests
                .map(userConverter::convertRequestToEntity)
                .flatMap(user -> Mono.fromCallable(() -> userRepository.save(user)))
                .map(User::getId)
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

    @Override
    public Flux<UserDto> findUserByEmailAndByUsername(Mono<UserSearchRequest> userSearchRequest) {
        return userSearchRequest
                .flatMap(searchRequest -> {
                    Pageable pageable = PageRequest.of(searchRequest.getPageNo(), searchRequest.getPageSize());
                    return Mono.fromCallable(() -> criteriaUserService.findUserByEmailAndByUsername(
                            pageable,
                            searchRequest.getEmailSearch(),
                            searchRequest.getUsername()));
                })
                .flatMapIterable(Slice::getContent)
                .map(userConverter::convertEntityToDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UserDto> findUserById(UUID idRequest) {
        return Mono.fromCallable(() -> userRepository.findUserById(idRequest))
                .map(userConverter::convertEntityToDto);
    }
}
