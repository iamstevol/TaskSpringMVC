package com.iamstevol.Task_Tracker.service;

import com.iamstevol.Task_Tracker.dto.UserRequestDto;
import com.iamstevol.Task_Tracker.model.User;

public interface UserService {

    User loginUser(UserRequestDto loginDto);
    User registerUser(UserRequestDto userRequestDto);
}
