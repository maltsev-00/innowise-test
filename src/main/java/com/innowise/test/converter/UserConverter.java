package com.innowise.test.converter;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {
    List<UserDto> convertEntityToDto(List<User> users);
}
