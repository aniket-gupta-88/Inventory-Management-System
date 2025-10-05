package com.myproject.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myproject.InventoryManagementSystem.enums.TransactionStatus;
import com.myproject.InventoryManagementSystem.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // read
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {

    private Long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;

    private TransactionType transactionType;

    private TransactionStatus status;

    private String description;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    private UserDto user;

    private ProductDto product;

    private SupplierDto supplier;

}
