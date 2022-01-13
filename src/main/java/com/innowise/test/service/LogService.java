package com.innowise.test.service;

import reactor.core.publisher.Mono;

public interface LogService {

    Mono<Void> save(String request);
}
