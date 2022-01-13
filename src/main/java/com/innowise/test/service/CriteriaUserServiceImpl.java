package com.innowise.test.service;

import com.innowise.test.model.SearchCriteria;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.enums.SearchOperation;
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
    private final SpecificationUserService specificationUserService;

    private static final String EMAIL_COLUMN = "email";

    @Override
    @Transactional(readOnly = true)
    public Page<User> findUserByEmailAndSortByUsername(Pageable pageRequest, String email) {
        Specification<User> searchSpec = specificationUserService.search(
                SearchCriteria.builder()
                        .key(EMAIL_COLUMN)
                        .value(email)
                        .operation(SearchOperation.EQUAL)
                        .build());
        return userRepository.findAll(searchSpec, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

}
