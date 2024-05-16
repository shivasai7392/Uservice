package com.ps.uservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ps.uservice.dtos.*;
import com.ps.uservice.models.Session;
import com.ps.uservice.models.SessionStatus;
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
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto) throws JsonProcessingException {
        return this.authService.login(requestDto.getEmailId(), requestDto.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto requestDto){
        UserDto userDto = this.authService.signUp(requestDto.getEmailId(), requestDto.getPassword(), requestDto.getName(), requestDto.getRoleTypes());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto requestDto){
        return this.authService.validate(requestDto.getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto){
        this.authService.logout(requestDto.getUserId(), requestDto.getToken());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
