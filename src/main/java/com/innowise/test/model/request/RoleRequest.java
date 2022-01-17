package com.innowise.test.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RoleRequest {
    @Length(max = 15)
    @NotNull
    private String name;
}
