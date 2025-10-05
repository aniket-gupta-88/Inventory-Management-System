package com.myproject.InventoryManagementSystem.service.impl;

import com.myproject.InventoryManagementSystem.dto.ProductDto;
import com.myproject.InventoryManagementSystem.dto.Response;
import com.myproject.InventoryManagementSystem.entity.Category;
import com.myproject.InventoryManagementSystem.entity.Product;
import com.myproject.InventoryManagementSystem.exceptions.ResourceNotFoundException;
import com.myproject.InventoryManagementSystem.repository.CategoryRepository;
import com.myproject.InventoryManagementSystem.repository.ProductRepository;
import com.myproject.InventoryManagementSystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir")+"/product-image/";

    @Override
    public Response saveProduct(ProductDto productDto, MultipartFile imageFile) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));

        Product productToSave = Product.builder()
                .name(productDto.getName())
                .sku(productDto.getSku())
                .price(productDto.getPrice())
                .stockQuantity(productDto.getStockQuantity())
                .description(productDto.getDescription())
                .category(category)
                .build();

        if(imageFile != null){
            String imagePath = saveImage(imageFile);
            productToSave.setImageUrl(imagePath);
        }

        productRepository.save(productToSave);

        return Response.builder()
                .status(200)
                .message("Product Successfully saved")
                .build();

    }

    @Override
    public Response updateProduct(ProductDto productDto, MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(productDto.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
        //check if image is associated with the update request
        if(imageFile != null && !imageFile.isEmpty()){
            String imagePath = saveImage(imageFile);
            existingProduct.setImageUrl(imagePath);
        }
        //check if category is changed
        if(productDto.getCategoryId() != null && productDto.getCategoryId() > 0){
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
            existingProduct.setCategory(category);
        }
        //check and update fields
        if(productDto.getName() != null && !productDto.getName().isBlank()){
            existingProduct.setName(productDto.getName());
        }

        if (productDto.getSku() !=null && !productDto.getSku().isBlank()){
            existingProduct.setSku(productDto.getSku());
        }

        if (productDto.getDescription() !=null && !productDto.getDescription().isBlank()){
            existingProduct.setDescription(productDto.getDescription());
        }

        if (productDto.getPrice() !=null && productDto.getPrice().compareTo(BigDecimal.ZERO) >=0){
            existingProduct.setPrice(productDto.getPrice());
        }

        if (productDto.getStockQuantity() !=null && productDto.getStockQuantity() >=0){
            existingProduct.setStockQuantity(productDto.getStockQuantity());
        }

        //Update the product
        productRepository.save(existingProduct);
        return Response.builder()
                .status(200)
                .message("Product successfully Updated")
                .build();

    }

    @Override
    public Response getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProductDto> productDtos = modelMapper.map(products, new TypeToken<List<ProductDto>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDtos)
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));


        return Response.builder()
                .status(200)
                .message("success")
                .product(modelMapper.map(product, ProductDto.class))
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        productRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Product successfully deleted")
                .build();
    }

    private String saveImage(MultipartFile imageFile){
        //validate image check
        if(!imageFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("Only image files are allowed");
        }
        //create the directory to store images if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);
        if(!directory.exists()){
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        //get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try {
            File destination = new File(imagePath);
            imageFile.transferTo(destination);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error Occured while saving image" + e.getMessage());
        }

        return imagePath;
    }

}
