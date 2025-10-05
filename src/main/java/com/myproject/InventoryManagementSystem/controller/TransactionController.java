package com.myproject.InventoryManagementSystem.controller;

import com.myproject.InventoryManagementSystem.dto.CategoryDto;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.TransactionRequest;
import com.myproject.InventoryManagementSystem.enums.TransactionStatus;
import com.myproject.InventoryManagementSystem.service.CategoryService;
import com.myproject.InventoryManagementSystem.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Response> purchaseInventory(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.restockInventory(transactionRequest));
    }

    @PostMapping("/sell")
    public ResponseEntity<Response> sell(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.sell(transactionRequest));
    }

    @PostMapping("/return")
    public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.returnToSupplier(transactionRequest));
    }

    @GetMapping ("/all")
    public ResponseEntity<Response> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size,
            @RequestParam(required = false) String searchText
    ){
        return ResponseEntity.ok(transactionService.getAllTransactions(page,size,searchText));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/by-month-year")
    public ResponseEntity<Response> getAllTransactionByMonthAndYear(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(transactionService.getAllTransactionByMonthAndYear(month, year));
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Response> updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestBody @Valid TransactionStatus status){
        return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId, status));
    }


}
