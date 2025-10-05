package com.myproject.InventoryManagementSystem.controller;

import com.myproject.InventoryManagementSystem.dto.LoginRequest;
import com.myproject.InventoryManagementSystem.dto.RegisterRequest;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> userLogin(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

}
