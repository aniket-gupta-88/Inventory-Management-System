package com.myproject.InventoryManagementSystem.service.impl;

import com.myproject.InventoryManagementSystem.dto.LoginRequest;
import com.myproject.InventoryManagementSystem.dto.RegisterRequest;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.UserDto;
import com.myproject.InventoryManagementSystem.entity.User;
import com.myproject.InventoryManagementSystem.enums.UserRole;
import com.myproject.InventoryManagementSystem.exceptions.InvalidCredentialsException;
import com.myproject.InventoryManagementSystem.exceptions.ResourceNotFoundException;
import com.myproject.InventoryManagementSystem.repository.UserRepository;
import com.myproject.InventoryManagementSystem.security.JwtUtils;
import com.myproject.InventoryManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role = UserRole.MANAGER;
        if (registerRequest.getRole() != null) {
            role = registerRequest.getRole();
        }
        User userToSave = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("User created successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("password does not match");
        }
        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("user logged in successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 month")
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserDto> UserDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType()); // read

        UserDtos.forEach(UserDto -> UserDto.setTransactions(null));

        return Response.builder()
                .status(200)
                .message("success")
                .users(UserDtos)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        user.setTransactions(null);

        return user;
    }

    @Override
    public Response updateUser(Long id, UserDto userDto) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (userDto.getEmail() != null)
            existingUser.setEmail(userDto.getEmail());
        if (userDto.getName() != null)
            existingUser.setName(userDto.getName());
        if (userDto.getPhoneNumber() != null)
            existingUser.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getRole() != null)
            existingUser.setRole(userDto.getRole());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPhoneNumber(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("User Successfully updated")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {

        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User Successfully Deleted")
                .build();
    }

    @Override
    public Response getUserTransactions(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        userDto.getTransactions().forEach(transactionDto -> {
            transactionDto.setUser(null);
            transactionDto.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDto)
                .build();
    }
}
