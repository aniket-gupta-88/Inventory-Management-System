package com.myproject.InventoryManagementSystem.service.impl;

import com.myproject.InventoryManagementSystem.dto.CategoryDto;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.UserDto;
import com.myproject.InventoryManagementSystem.entity.Category;
import com.myproject.InventoryManagementSystem.entity.User;
import com.myproject.InventoryManagementSystem.exceptions.ResourceNotFoundException;
import com.myproject.InventoryManagementSystem.repository.CategoryRepository;
import com.myproject.InventoryManagementSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;


    @Override
    public Response createCategory(CategoryDto categoryDto) {
        Category categoryToSave = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<CategoryDto> categoryDtos = modelMapper.map(categories, new TypeToken<List<CategoryDto>>() {}.getType()); // read

        return Response.builder()
                .status(200)
                .message("success")
                .categories(categoryDtos)
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        return Response.builder()
                .status(200)
                .message("success")
                .category(categoryDto)
                .build();
    }

    @Override
    public Response updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));

        existingCategory.setName(categoryDto.getName());
        categoryRepository.save(existingCategory);

        return Response.builder()
                .status(200)
                .message("Category Successfully Updated")
                .build();
    }

    @Override
    public Response deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));

        categoryRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Category Deleted Successfully")
                .build();
    }
}
