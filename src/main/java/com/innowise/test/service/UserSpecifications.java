package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import com.innowise.test.model.meta.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    private static final String SPECIFICATION_PATTERN = "%";

    public static Specification<User> emailLike(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL),
                SPECIFICATION_PATTERN + email + SPECIFICATION_PATTERN);
    }

    public static Specification<User> usernameLike(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.USERNAME),
                SPECIFICATION_PATTERN + username + SPECIFICATION_PATTERN);
    }

}
