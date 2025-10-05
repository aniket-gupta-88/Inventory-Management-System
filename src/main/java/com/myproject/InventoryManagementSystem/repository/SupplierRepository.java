package com.myproject.InventoryManagementSystem.repository;

import com.myproject.InventoryManagementSystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier , Long> {
}
