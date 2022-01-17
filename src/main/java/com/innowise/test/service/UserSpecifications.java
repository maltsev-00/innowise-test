package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import com.innowise.test.model.meta.User_;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserSpecifications {

    public static Specification<User> emailLike(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL),
                 email);
    }

    public static Specification<User> usernameLike(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USERNAME),
                  username);
    }

    public static Specification<User> idEqual(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.ID), id);
    }
}
