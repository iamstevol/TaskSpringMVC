package com.iamstevol.Task_Tracker.mapper;

import com.iamstevol.Task_Tracker.dto.UserRequestDto;
import com.iamstevol.Task_Tracker.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserRequestDtoMapper implements Function<User, UserRequestDto> {

    @Override
    public UserRequestDto apply(User user) {
        return new UserRequestDto(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
