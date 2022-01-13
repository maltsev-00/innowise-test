package com.innowise.test.converter;

import com.innowise.test.model.dto.UserDto;
import com.innowise.test.model.entity.User;
import com.innowise.test.model.request.UserSaveRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {
    List<UserDto> convertEntityToDto(List<User> users);

    User convertRequestToEntity(UserSaveRequest userRequest);

    List<User> convertRequestToEntity(List<UserSaveRequest> userSaveRequests);

    UserDto convertEntityToDto(User user);

}
