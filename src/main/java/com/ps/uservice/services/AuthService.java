package com.ps.uservice.services;

import com.ps.uservice.dtos.UserDto;
import com.ps.uservice.models.User;
import com.ps.uservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class AuthService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String emailid, String password){
        Optional<User> userOptional = this.userRepository.findByEmailId(emailid);

        if (userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            return null;
        }

        String token = RandomStringUtils.randomAlphabetic(30);

        return null;
    }
}
