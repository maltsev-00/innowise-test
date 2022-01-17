package com.innowise.test.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private int pageNo;
    @Min(1)
    private int pageSize;
    private UUID idUser;
    private String email;
    private String username;
}
