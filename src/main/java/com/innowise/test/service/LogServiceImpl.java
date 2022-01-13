package com.innowise.test.service;

import com.innowise.test.exception.LogFileWriteException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class LogServiceImpl implements LogService {

    private static final String LOG_FILE_NAME = "LogUserFile.txt";

    @Override
    public Mono<Void> save(String request) {
        return Mono.fromCallable(() -> {
            writeInFile(request);
            return Mono.empty();
        }).then();
    }

    private void writeInFile(String request) {
        try {
            FileWriter fileWriter = new FileWriter(LOG_FILE_NAME, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(request + "\n");
            printWriter.close();
        } catch (IOException e) {
            throw new LogFileWriteException(e.getMessage());
        }
    }

}
