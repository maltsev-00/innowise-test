package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CriteriaUserServiceImpl implements CriteriaUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findUserByEmailAndByUsername(String email, String username) {
        return userRepository.findAll(
                Specification
                        .where(UserSpecifications.emailLike(email))
                        .and(UserSpecifications.usernameLike(username)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findOne(Specification.where(UserSpecifications.usernameLike(username)))
                .orElseGet(User::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(UUID id) {
        return userRepository.findOne(Specification.where(UserSpecifications.idEqual(id)))
                .orElseGet(User::new);
    }

}
