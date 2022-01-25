package com.innowise.test.service;

import com.innowise.test.converter.UserInfoConverter;
import com.innowise.test.exception.UserNotFoundException;
import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.PhotoUser;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.request.UserPhotoRequest;
import com.innowise.test.model.request.UserRequest;
import com.innowise.test.model.request.UserSaveRequest;
import com.innowise.test.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoConverter userInfoConverter;
    private final CriteriaUserInfoService criteriaUserInfoService;

    private static final String USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "User not found with id: %s";

    @Override
    public Flux<UserDto> getUsers(Mono<UserRequest> userRequest) {
        return userRequest
                .flatMap(request -> {
                    PageRequest page = PageRequest.of(request.getPageNo(), request.getPageSize());
                    return Mono.fromCallable(() -> criteriaUserInfoService.findAll(page, request.getIdUser(), request.getUsername(), request.getEmail()));
                })
                .flatMapIterable(Slice::getContent)
                .map(userInfoConverter::convertEntityToDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UUID> saveUser(Mono<UserSaveRequest> userSaveRequest) {
        return userSaveRequest
                .map(userInfoConverter::convertRequestToEntity)
                .flatMap(user -> Mono.fromCallable(() -> userInfoRepository.save(user)))
                .map(User::getId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<UUID> saveUsers(Flux<UserSaveRequest> userSaveRequests) {
        return userSaveRequests
                .map(userInfoConverter::convertRequestToEntity)
                .flatMap(user -> Mono.fromCallable(() -> userInfoRepository.save(user)))
                .map(User::getId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteUser(UUID idUser) {
        return Mono.fromCallable(() -> userInfoRepository.findUserById(idUser))
                .switchIfEmpty(Mono.error(new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, idUser))))
                .map(user -> {
                    userInfoRepository.delete(user);
                    return Mono.empty();
                })
                .then()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UUID> saveUserPhoto(UserPhotoRequest userPhotoRequest) {
        return Mono.fromCallable(() -> criteriaUserInfoService.findById(userPhotoRequest.getIdUser()))
                .onErrorResume(error -> Mono.error(new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, userPhotoRequest.getIdUser()))))
                .flatMap(user -> {
                    var photosUser = user.getPhotoUsers();
                    photosUser.add(PhotoUser.builder()
                            .photoId(userPhotoRequest.getIdPhoto())
                            .build());
                    user.setPhotoUsers(photosUser);
                    return Mono.fromCallable(() -> userInfoRepository.save(user));
                })
                .map(User::getId);
    }
}
