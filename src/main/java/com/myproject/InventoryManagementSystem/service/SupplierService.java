package com.myproject.InventoryManagementSystem.service;

import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.SupplierDto;

public interface SupplierService {

    Response addSupplier(SupplierDto supplierDto);

    Response getAllSuppliers();

    Response getSupplierById(Long id);

    Response updateSupplier(Long id, SupplierDto supplierDto);

    Response deleteSupplier(Long id);

}
