package com.innowise.test.model.dto;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
@Builder
public class RoleDto {
    @Length(max = 15)
    String name;
}
