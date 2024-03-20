package com.ps.uservice.controllers;

import com.ps.uservice.dtos.LoginRequestDto;
import com.ps.uservice.dtos.SignUpRequestDto;
import com.ps.uservice.dtos.UserDto;
import com.ps.uservice.services.AuthService;
import com.ps.uservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto){
        return this.authService.login(requestDto.getEmailId(), requestDto.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto requestDto){
        UserDto userDto = this.authService.signUp(requestDto.getEmailId(), requestDto.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
