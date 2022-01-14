package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import com.innowise.test.model.meta.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> emailLike(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL),
                "%" + email + "%");
    }

    public static Specification<User> usernameLike(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.USERNAME),
                "%" + username + "%");
    }

}
