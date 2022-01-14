package com.innowise.test.converter;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.request.UserSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User convertRequestToEntity(UserSaveRequest userRequest);

    UserDto convertEntityToDto(User user);

}
