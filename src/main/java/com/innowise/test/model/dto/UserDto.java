package com.innowise.test.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class UserDto {
    UUID id;
    String username;
    String email;
    Set<RoleDto> roles;
    Set<CourseDto> courses;
    CardDto card;
}
