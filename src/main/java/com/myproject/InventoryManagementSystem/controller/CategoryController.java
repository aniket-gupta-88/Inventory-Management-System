package com.myproject.InventoryManagementSystem.controller;

import com.myproject.InventoryManagementSystem.dto.CategoryDto;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @GetMapping ("/all")
    public ResponseEntity<Response> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

}
