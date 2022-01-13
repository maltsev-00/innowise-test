package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriteriaUserService {
    Page<User> findUserByEmailAndSortByUsername(Pageable pageable, String email);

    Page<User> findAll(Pageable pageable);
}
