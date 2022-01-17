package com.innowise.test.service;

import com.innowise.test.model.entity.User;
import com.innowise.test.model.meta.User_;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static java.util.Optional.ofNullable;

public class UserSpecifications {

    public static Specification<User> emailLike(String email) {
        return (root, query, criteriaBuilder) ->
                ofNullable(email)
                        .filter(StringUtils::isNotBlank)
                        .map(value -> criteriaBuilder.like(root.get(User_.EMAIL), email))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<User> usernameLike(String username) {
        return (root, query, criteriaBuilder) ->
                ofNullable(username)
                        .filter(StringUtils::isNotBlank)
                        .map(value -> criteriaBuilder.like(root.get(User_.USERNAME), username))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<User> idEqual(UUID id) {
        return (root, query, criteriaBuilder) ->
                ofNullable(id)
                        .map(value -> criteriaBuilder.equal(root.get(User_.EMAIL), id))
                        .orElseGet(criteriaBuilder::conjunction);
    }
}
