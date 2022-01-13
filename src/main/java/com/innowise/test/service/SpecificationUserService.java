package com.innowise.test.service;

import com.innowise.test.model.SearchCriteria;
import com.innowise.test.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationUserService {
    Specification<User> search(SearchCriteria criteria);
}
