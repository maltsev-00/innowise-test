package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriteriaUserService {
    Page<User> findUserByEmailAndByUsername(Pageable pageable, String email, String username);

    Page<User> findAll(Pageable pageable);
}
