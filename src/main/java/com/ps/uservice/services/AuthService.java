package com.ps.uservice.services;

import com.ps.uservice.dtos.UserDto;
import com.ps.uservice.models.Session;
import com.ps.uservice.models.SessionStatus;
import com.ps.uservice.models.User;
import com.ps.uservice.repositories.SessionRepository;
import com.ps.uservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String emailid, String password){
        Optional<User> userOptional = this.userRepository.findByEmailId(emailid);
        UserDto userDto = new UserDto();

        if (userOptional.isEmpty()){
            return new ResponseEntity<>(userDto, HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            return new ResponseEntity<>(userDto, HttpStatus.UNAUTHORIZED);
        }

        String token = RandomStringUtils.randomAlphabetic(30);

        Session session = new Session();
        session.setStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        userDto.setEmailId(emailid);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    public UserDto signUp(String emailId, String password){
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setEmailId(savedUser.getEmailId());

        return userDto;
    }
}
