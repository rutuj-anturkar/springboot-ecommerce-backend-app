package com.ecommerce.controller;

import com.ecommerce.dto.AddProductDTO;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping
    ResponseEntity<?> addProducts(@RequestBody AddProductDTO addProductDTO){
        productService.addProduct(addProductDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    ResponseEntity<?> getProductById(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @PutMapping("/update/{productId}")
    ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody AddProductDTO addProductDTO){
        productService.updateProduct(productId, addProductDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    ResponseEntity<?> getAllProducts(Principal principal){
        System.out.println("Principal in product controller" + principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/category/{categoryId}")
    ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategoryId(categoryId));
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<?> deleteProductsById(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
