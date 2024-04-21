package com.ps.uservice.security;

import com.ps.uservice.models.User;
import com.ps.uservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByEmailId(username);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("");
        }
        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}
