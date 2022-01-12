package com.innowise.test.model.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
   @Length(max = 100)
   private String name;
}
