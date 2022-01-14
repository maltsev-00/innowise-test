package com.innowise.test;

import com.innowise.test.model.entity.User;
import com.innowise.test.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserInfoServiceTests {


    @Autowired
    private UserRepository repository;

    @Test
    public void shouldSaveUser() {
        String TEST_EMAIL = "test@gmail.com";
        User user = User.builder()

                .build();
        repository.deleteAll();
        Publisher<User> setup = Mono.fromCallable(() -> repository.save(user));
        Mono<User> find = Mono.fromCallable(() -> repository.findUserById(UUID.randomUUID()));
        Publisher<User> composite = Mono
                .from(setup)
                .then(find);
        StepVerifier
                .create(composite)
                .consumeNextWith(account -> {
                    assertNotNull(account.getId());
                    assertEquals(account.getEmail(), TEST_EMAIL);
                    assertEquals(account.getUsername(), "username");
                    assertEquals(account.getEmail(), "test@gmail.com");
                })
                .verifyComplete();
    }

}
