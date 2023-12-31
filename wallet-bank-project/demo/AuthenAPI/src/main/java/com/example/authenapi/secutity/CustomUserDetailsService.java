package com.example.authenapi.secutity;

import com.example.authenapi.entity.User;
import com.example.authenapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Not found user with email = " + email);
        }
        return CustomUserDetails.builder()
                .user(userOptional.get())
                .build();
    }
}