package com.ps.uservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.uservice.dtos.UserDto;
import com.ps.uservice.models.*;
import com.ps.uservice.repositories.SessionRepository;
import com.ps.uservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<UserDto> login(String emailId, String password) throws JsonProcessingException {
        Optional<User> userOptional = this.userRepository.findByEmailId(emailId);
        UserDto userDto = new UserDto();

        if (userOptional.isEmpty()){
            return new ResponseEntity<>(userDto, HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            return new ResponseEntity<>(userDto, HttpStatus.UNAUTHORIZED);
        }

        userDto.setEmailId(emailId);
        userDto.setRoles(user.getRoles());
        userDto.setId(user.getId());
        userDto.setName(user.getName());

        List<Session> sessions = this.sessionRepository.findAllByUserIdAndStatusAndExpiryingAtAfter(user.getId(), SessionStatus.ACTIVE, new Date());
        if (sessions.size() == 2){
            return new ResponseEntity<>(userDto, HttpStatus.UNAUTHORIZED);
        }
//        String token = RandomStringUtils.randomAlphabetic(30);

        // Create a test key suitable for the desired HMAC-SHA algorithm:
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();

        String message = this.objectMapper.writeValueAsString(user);
        byte[] content = message.getBytes(StandardCharsets.UTF_8);

        // Create the compact JWS:
        String jws = Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();

        // Parse the compact JWS:
        content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();

        assert message.equals(new String(content, StandardCharsets.UTF_8));

        Session session = new Session();
        session.setStatus(SessionStatus.ACTIVE);
        session.setToken(jws);
        session.setUser(user);
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jws);

        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    public UserDto signUp(String emailId, String password, String name, List<RoleType> roleTypes){
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setName(name);
        Set<Role> roles = new HashSet<>();
        for (RoleType roleType : roleTypes){
            Role role = new Role();
            role.setRoleType(roleType);
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setEmailId(savedUser.getEmailId());
        userDto.setRoles(savedUser.getRoles());
        userDto.setId(savedUser.getId());
        userDto.setName(savedUser.getName());

        return userDto;
    }

    public SessionStatus validate(UUID userId, String token){
        Optional<Session> sessionOptional = this.sessionRepository.findByUserIdAndToken(userId, token);
        if (sessionOptional.isEmpty()){
            return null;
        }
        Session session = sessionOptional.get();
        return SessionStatus.ACTIVE;
    }

    public ResponseEntity<Void> logout(UUID userId, String token){
        Optional<Session> sessionOptional = this.sessionRepository.findByUserIdAndToken(userId, token);
        if (sessionOptional.isEmpty()){
            return null;
        }

        Session session = sessionOptional.get();
        session.setStatus(SessionStatus.ENDED);

        this.sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }


}
