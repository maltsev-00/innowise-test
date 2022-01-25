package com.innowise.test.repository;

import com.innowise.test.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<User, UUID>, JpaSpecificationExecutor<User> {
    User deleteUserById(UUID id);

    User findUserById(UUID id);
}
