package com.myproject.InventoryManagementSystem.repository;

import com.myproject.InventoryManagementSystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
