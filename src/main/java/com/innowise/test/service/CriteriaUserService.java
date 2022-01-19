package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CriteriaUserService {

    Page<User> findAll(Pageable pageable, UUID idUser, String username, String email);

    User findById(UUID idUser);
}
