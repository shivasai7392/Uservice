package com.ps.uservice.services;

import com.ps.uservice.dtos.UserDto;
import com.ps.uservice.models.User;
import com.ps.uservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserDetails(Long userid){
        User user = this.userRepository.getReferenceById(UUID.fromString(""));
        UserDto userDto = new UserDto();
        userDto.setEmailId(user.getEmailId());
        userDto.setName(user.getName());
        return userDto;
    }

    public UserDto login(String emailId, String password){
        UserDto userDto = new UserDto();
        return userDto;
    }
}
