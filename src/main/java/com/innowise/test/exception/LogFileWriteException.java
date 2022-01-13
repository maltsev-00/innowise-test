package com.innowise.test.exception;

public class LogFileWriteException extends RuntimeException {
    public LogFileWriteException(String message) {
        super(message);
    }
}