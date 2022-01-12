package com.innowise.test.repository;

import com.innowise.test.model.entity.User;
import com.innowise.test.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCriteriaRepository {


    public Page<User> findAllWithFilters(UserRequest userRequest) {
//        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
//        Root<User> employeeRoot = criteriaQuery.from(User.class);
//        Predicate predicate = getPredicate(userRequest, employeeRoot);
//        criteriaQuery.where(predicate);
//        setOrder(userRequest, criteriaQuery, employeeRoot);
//
//        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setFirstResult(userRequest.getPageNo() * userRequest.getPageSize());
//        typedQuery.setMaxResults(userRequest.getPageSize());
//
//        Pageable pageable = getPageable(employeePage);
//
//        long employeesCount = getEmployeesCount(predicate);

//        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
        return null;
    }


}
