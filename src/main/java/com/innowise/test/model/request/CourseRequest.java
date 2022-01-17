package com.innowise.test.model.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
   @Length(max = 100)
   @NotNull
   @NotEmpty
   private String name;
}
