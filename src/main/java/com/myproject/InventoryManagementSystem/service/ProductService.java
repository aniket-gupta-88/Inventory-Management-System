package com.myproject.InventoryManagementSystem.service;

import com.myproject.InventoryManagementSystem.dto.ProductDto;
import com.myproject.InventoryManagementSystem.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Response saveProduct(ProductDto productDto, MultipartFile imageFile);

    Response updateProduct(ProductDto productDto, MultipartFile imageFile);

    Response getAllProducts();

    Response getProductById(Long id);

    Response deleteProduct(Long id);
}
