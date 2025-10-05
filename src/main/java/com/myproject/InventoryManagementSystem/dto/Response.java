package com.myproject.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myproject.InventoryManagementSystem.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int status;

    private String message;

    // for login
    private String token;
    private UserRole role;
    private String expirationTime;

    //for pagination
    private Integer totalPages;
    private Long totalElements;

    //data output optional
    private UserDto user;
    private List<UserDto> users;

    private SupplierDto supplier;
    private List<SupplierDto> suppliers;

    private CategoryDto category;
    private List<CategoryDto> categories;

    private ProductDto product;
    private List<ProductDto> products;

    private TransactionDto transaction;
    private List<TransactionDto> transactions;

    private final LocalDateTime timestamp = LocalDateTime.now();

}
