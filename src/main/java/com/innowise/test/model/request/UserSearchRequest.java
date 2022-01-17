package com.innowise.test.model.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserSearchRequest {
    String emailSearch;
    String username;
}
