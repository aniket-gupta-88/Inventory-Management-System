package com.myproject.InventoryManagementSystem.service;

import com.myproject.InventoryManagementSystem.dto.LoginRequest;
import com.myproject.InventoryManagementSystem.dto.RegisterRequest;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.UserDto;
import com.myproject.InventoryManagementSystem.entity.User;

public interface UserService {

    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response updateUser(Long id, UserDto userDto);

    Response deleteUser(Long id);

    Response getUserTransactions(Long id);

}
