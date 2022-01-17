package com.innowise.test;

import com.innowise.test.model.entity.Card;
import com.innowise.test.model.entity.Course;
import com.innowise.test.model.entity.Role;
import com.innowise.test.model.entity.User;
import com.innowise.test.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserInfoServiceTests {


    @MockBean
    private UserRepository repository;

    @Test
    public void shouldSaveUser() {
        String TEST_EMAIL = "test@gmail.com";
        UUID idUser = UUID.fromString("123e4567-e89b-12d3-a456-556642440000");
        String username = "username";
        User user = new User();
        user.setRoles(Collections.singleton(new Role()));
        user.setUsername(username);
        user.setEmail(TEST_EMAIL);
        user.setId(idUser);
        user.setCourses(Collections.singleton(new Course()));
        user.setCard(new Card());

        repository.deleteAll();
        Publisher<User> setup = Mono.fromCallable(() -> repository.save(user));
        Mono<User> find = Mono.fromCallable(() -> repository.findUserById(idUser));
        Publisher<User> composite = Mono
                .from(setup)
                .then(find);
        StepVerifier
                .create(composite)
                .consumeNextWith(account -> {
                    assertNotNull(account.getId());
                    assertEquals(account.getEmail(), TEST_EMAIL);
                    assertEquals(account.getId(), idUser);
                    assertEquals(account.getUsername(), username);
                })
                .expectComplete()
                .verify();
    }

}
