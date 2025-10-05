package com.myproject.InventoryManagementSystem.service;


import com.myproject.InventoryManagementSystem.dto.CategoryDto;
import com.myproject.InventoryManagementSystem.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryDto);

    Response getAllCategories();

    Response getCategoryById(Long id);

    Response updateCategory(Long id, CategoryDto categoryDto);

    Response deleteCategory(Long id);

}
