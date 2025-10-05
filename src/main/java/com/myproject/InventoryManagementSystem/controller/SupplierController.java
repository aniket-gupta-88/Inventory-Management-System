package com.myproject.InventoryManagementSystem.controller;

import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.SupplierDto;
import com.myproject.InventoryManagementSystem.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping ("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addSupplier(@RequestBody @Valid SupplierDto supplierDto){
        return ResponseEntity.ok(supplierService.addSupplier(supplierDto));
    }

    @GetMapping ("/all")
    public ResponseEntity<Response> getAllSuppliers(){
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSupplierById(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody SupplierDto supplierDto){
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }


}
