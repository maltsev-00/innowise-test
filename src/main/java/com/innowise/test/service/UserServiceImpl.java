package com.innowise.test.service;

import com.innowise.test.converter.UserConverter;
import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.model.request.UserSearchRequest;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CriteriaUserService criteriaUserService;

    @Override
    public Mono<List<UserDto>> findAll(UserRequest userRequest) {
        return Mono.fromCallable(() -> {
                    Pageable pageable = PageRequest.of(userRequest.getPageNo(), userRequest.getPageSize());
                    return criteriaUserService.findAll(pageable);
                })
                .map(pageable -> userConverter.convertEntityToDto(pageable.getContent()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UUID> save(UserSaveRequest userSaveRequest) {
        return Mono.fromCallable(() -> userSaveRequest)
                .map(userRequest -> {
                    User user = userConverter.convertRequestToEntity(userRequest);
                    userRepository.save(user);
                    return user.getId();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<List<UUID>> saveList(List<UserSaveRequest> userSaveRequests) {
        return Mono.fromCallable(() -> userSaveRequests)
                .map(usersSaveRequest -> {
                    List<User> users = userConverter.convertRequestToEntity(usersSaveRequest);
                    userRepository.saveAll(users);
                    return users.stream()
                            .map(User::getId)
                            .collect(Collectors.toList());
                })
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
    public Mono<List<UserDto>> findUsersByUsernameAndSort(UserSearchRequest userSearchRequest) {
        return Mono.fromCallable(() -> {
                    Pageable pageable = PageRequest.of(userSearchRequest.getPageNo(), userSearchRequest.getPageSize());
                    return criteriaUserService.findUserByEmailAndSortByUsername(pageable, userSearchRequest.getEmailSearch());
                })
                .map(pageable -> userConverter.convertEntityToDto(pageable.getContent()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UserDto> findUserById(UUID id) {
        return Mono.fromCallable(() -> userRepository.findUserById(id))
                .map(userConverter::convertEntityToDto);
    }
}
