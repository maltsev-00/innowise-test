package com.innowise.test.service;

import com.innowise.test.exception.UserNotFoundException;
import com.innowise.test.model.entity.User;
import com.innowise.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;


@Service
@RequiredArgsConstructor
public class CriteriaUserServiceImpl implements CriteriaUserService {

    private final UserRepository userRepository;

    private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User not found with id: %s";

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable, UUID idUser, String username, String email) {
        return userRepository.findAll(
                where(UserSpecifications.idEqual(idUser)
                        .and(UserSpecifications.usernameLike(username))
                        .and(UserSpecifications.emailLike(email))), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID idUser) {
        Optional<User> userOptional = userRepository.findOne(where(UserSpecifications.idEqual(idUser)));
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, idUser));
        }
    }
}
