package com.myproject.InventoryManagementSystem.controller;

import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.UserDto;
import com.myproject.InventoryManagementSystem.entity.User;
import com.myproject.InventoryManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/transactions/{userid}")
    public ResponseEntity<Response> getUserAndTransactions(@PathVariable Long userid) {
        return ResponseEntity.ok(userService.getUserTransactions(userid));
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentLoggedInUser());
    }

}
