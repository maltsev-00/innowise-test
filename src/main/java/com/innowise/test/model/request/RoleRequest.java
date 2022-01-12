package com.innowise.test.model.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RoleRequest {
   @Length(max = 15)
   private String name;
}
