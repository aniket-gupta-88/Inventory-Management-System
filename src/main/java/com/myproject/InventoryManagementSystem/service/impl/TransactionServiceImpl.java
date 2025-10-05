package com.myproject.InventoryManagementSystem.service.impl;

import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.dto.TransactionDto;
import com.myproject.InventoryManagementSystem.dto.TransactionRequest;
import com.myproject.InventoryManagementSystem.entity.Product;
import com.myproject.InventoryManagementSystem.entity.Supplier;
import com.myproject.InventoryManagementSystem.entity.Transaction;
import com.myproject.InventoryManagementSystem.entity.User;
import com.myproject.InventoryManagementSystem.enums.TransactionStatus;
import com.myproject.InventoryManagementSystem.enums.TransactionType;
import com.myproject.InventoryManagementSystem.exceptions.NameValueRequiredException;
import com.myproject.InventoryManagementSystem.exceptions.ResourceNotFoundException;
import com.myproject.InventoryManagementSystem.repository.ProductRepository;
import com.myproject.InventoryManagementSystem.repository.SupplierRepository;
import com.myproject.InventoryManagementSystem.repository.TransactionRepository;
import com.myproject.InventoryManagementSystem.service.TransactionService;
import com.myproject.InventoryManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;
    private final UserService userService;
    private final ProductRepository productRepository;


    @Override
    public Response restockInventory(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if(supplierId == null) throw new NameValueRequiredException("Supplier Id is required");

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()-> new ResourceNotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Transaction Made Successfully")
                .build();

    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {

        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        User user = userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.SALE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Sold Successfully")
                .build();
    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if(supplierId == null) throw new NameValueRequiredException("Supplier Id is required");

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()-> new ResourceNotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .status(TransactionStatus.PROCESSING)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Returned Successfully Initialized")
                .build();
    }

    @Override
    public Response getAllTransactions(int page, int size, String searchText) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Transaction> transactionPage = transactionRepository.searchTransactions(searchText, pageable);

        List<TransactionDto> transactionDtoList = modelMapper.map(transactionPage.getContent(), new TypeToken<List<TransactionDto>>() {}.getType());

        transactionDtoList.forEach(transactionDtoItem ->{
            transactionDtoItem.setUser(null);
            transactionDtoItem.setProduct(null);
            transactionDtoItem.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDtoList)
                .build();

    }

    @Override
    public Response getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Transaction Not Found"));

        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);

        transactionDto.getUser().setTransactions(null); // removing the user transaction list

        return Response.builder()
                .status(200)
                .message("success")
                .transaction(transactionDto)
                .build();
    }

    @Override
    public Response getAllTransactionByMonthAndYear(int month, int year) {
        List<Transaction> transactions = transactionRepository.findAllByMonthAndYear(month, year);

        List<TransactionDto> transactionDtoList = modelMapper.map(transactions, new TypeToken<List<TransactionDto>>() {}.getType());

        transactionDtoList.forEach(transactionDtoItem ->{
            transactionDtoItem.setUser(null);
            transactionDtoItem.setProduct(null);
            transactionDtoItem.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDtoList)
                .build();

    }

    @Override
    public Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus) {
        Transaction existingtransaction = transactionRepository.findById(transactionId).orElseThrow(()-> new ResourceNotFoundException("Transaction Not Found"));

        existingtransaction.setStatus(transactionStatus);
        existingtransaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(existingtransaction);

        return Response.builder()
                .status(200)
                .message("Transaction Status Successfully Updated")
                .build();
    }
}
