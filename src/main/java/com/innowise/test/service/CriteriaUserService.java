package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CriteriaUserService {
    List<User> findUserByEmailAndByUsername(String email, String username);

    Page<User> findAll(Pageable pageable);

    User findUserByUsername(String username);

    User findUserById(UUID id);

}
