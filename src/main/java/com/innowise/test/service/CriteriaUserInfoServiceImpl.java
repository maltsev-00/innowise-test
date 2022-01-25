package com.innowise.test.service;

import com.innowise.test.exception.UserNotFoundException;
import com.innowise.test.model.entity.User;
import com.innowise.test.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;


@Service
@RequiredArgsConstructor
public class CriteriaUserInfoServiceImpl implements CriteriaUserInfoService {

    private final UserInfoRepository userInfoRepository;

    private static final String USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE = "User not found with id: %s";

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable, UUID idUser, String username, String email) {
        return userInfoRepository.findAll(
                where(UserInfoSpecifications.idEqual(idUser)
                        .and(UserInfoSpecifications.usernameLike(username))
                        .and(UserInfoSpecifications.emailLike(email))), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID idUser) {
        Optional<User> userOptional = userInfoRepository.findOne(where(UserInfoSpecifications.idEqual(idUser)));
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, idUser));
        }
    }
}
