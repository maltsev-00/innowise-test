package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CriteriaUserServiceImpl implements CriteriaUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<User> findUserByEmailAndByUsername(Pageable pageRequest, String email, String username) {
        return userRepository.findAll(
                Specification
                        .where(UserSpecifications.emailLike(email))
                        .and(UserSpecifications.usernameLike(username)),
                pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

}
