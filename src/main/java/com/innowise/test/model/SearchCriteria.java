package com.innowise.test.model;

import com.innowise.test.model.enums.SearchOperation;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchCriteria {
    String key;
    Object value;
    SearchOperation operation;
}
