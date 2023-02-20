package com.iamstevol.Task_Tracker.service.Impl;

import com.iamstevol.Task_Tracker.dto.ResponseDto;
import com.iamstevol.Task_Tracker.dto.UserRequestDto;
import com.iamstevol.Task_Tracker.model.User;
import com.iamstevol.Task_Tracker.repository.UserRepository;
import com.iamstevol.Task_Tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User loginUser(UserRequestDto loginDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()));
        ResponseDto response = new ResponseDto();

        if(user.isPresent()) {
            response.setStatus(true);
            response.setData(user.get());
            response.setMessage("Login successful");
            return response.getData();
        } else {
            response.setMessage("Invalid Login");
            return response.getData();
        }
    }

    @Override
    public User registerUser(UserRequestDto userRequestDto) {

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setUsername(userRequestDto.getUsername());
        return userRepository.save(user);
    }
}
