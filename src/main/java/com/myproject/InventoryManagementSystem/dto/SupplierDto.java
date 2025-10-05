package com.myproject.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // read
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String address;
}
