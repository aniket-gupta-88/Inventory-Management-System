package com.myproject.InventoryManagementSystem.security;

import com.myproject.InventoryManagementSystem.entity.User;
import com.myproject.InventoryManagementSystem.exceptions.ResourceNotFoundException;
import com.myproject.InventoryManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor //read
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User Email Not Found"));

        return AuthUser.builder()
                .user(user)
                .build();
    }
}
