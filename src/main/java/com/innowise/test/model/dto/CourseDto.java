package com.innowise.test.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CourseDto {
    UUID id;
    String name;
}
