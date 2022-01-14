package com.innowise.test.model.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;

@Value
@Builder
public class UserSearchRequest {
    String emailSearch;
    int pageNo;
    @Min(1)
    int pageSize;
    String username;
}
