package com.ecommerce.service;

import com.ecommerce.dto.AddProductDTO;
import com.ecommerce.dto.ProductDetailsDTO;

import java.util.List;

public interface ProductService {
    void addProduct(AddProductDTO addProductDTO);

    ProductDetailsDTO getProductById(Long id);

    void updateProduct(Long id, AddProductDTO addProductDTO);

    List<ProductDetailsDTO> getAllProducts();

    void deleteProduct(Long productId);

    List<ProductDetailsDTO> getProductsByCategoryId(Long categoryId);
}
