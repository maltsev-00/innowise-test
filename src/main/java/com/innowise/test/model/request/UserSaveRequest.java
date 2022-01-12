package com.innowise.test.model.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Value
@Builder
public class UserSaveRequest {
    @Size(max = 50)
    String username;
    @Email
    String email;
    Set<RoleRequest> roles;
    CardRequest card;
    List<CourseRequest> courses;
}
