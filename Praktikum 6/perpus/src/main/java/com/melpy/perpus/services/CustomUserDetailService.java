package com.melpy.perpus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.melpy.perpus.entity.User;
import com.melpy.perpus.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    @Autowired 
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username:" + email);
        }
        UserDetails userDetails = 
        org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .build();
        return userDetails;
    }
}
