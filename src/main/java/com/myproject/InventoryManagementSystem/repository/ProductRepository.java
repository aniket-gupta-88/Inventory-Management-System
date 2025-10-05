package com.myproject.InventoryManagementSystem.repository;

import com.myproject.InventoryManagementSystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
