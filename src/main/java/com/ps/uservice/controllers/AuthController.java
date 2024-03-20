package com.ps.uservice.controllers;

import com.ps.uservice.dtos.LoginRequestDto;
import com.ps.uservice.dtos.UserDto;
import com.ps.uservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto){
        return this.userService.login(requestDto.getEmailId(), requestDto.getPassword());
    }
}
