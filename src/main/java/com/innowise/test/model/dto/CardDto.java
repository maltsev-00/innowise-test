package com.innowise.test.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CardDto {
    UUID id;
    int number;
}
