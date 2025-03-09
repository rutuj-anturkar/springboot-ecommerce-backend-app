package com.ecommerce.service;

import com.ecommerce.dto.AddProductDTO;
import com.ecommerce.dto.ProductDetailsDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void addProduct(AddProductDTO addProductDTO) {
        Category category = categoryRepository.findById(addProductDTO.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Product Scenario: Invalid Category"));
        if(productRepository.findByName(addProductDTO.getName()).isPresent()){
            throw new RuntimeException("Product exists");
        }
        Product product = modelMapper.map(addProductDTO,Product.class);
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public ProductDetailsDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        ProductDetailsDTO dto = modelMapper.map(product,ProductDetailsDTO.class);
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }

    @Override
    public void updateProduct(Long id, AddProductDTO addProductDTO){
        productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(addProductDTO.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Product Scenario: Invalid Category"));

        Product product = modelMapper.map(addProductDTO, Product.class);
        product.setId(id);
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public List<ProductDetailsDTO> getAllProducts(){
        return productRepository.findAll().stream().map((Product product)-> {
            ProductDetailsDTO result = modelMapper.map(product,ProductDetailsDTO.class);
            result.setCategoryId(product.getCategory().getId());
            result.setCategoryName(product.getCategory().getCategoryName());
            return result;
        }).toList();
    }

    @Override
    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDetailsDTO> getProductsByCategoryId(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("Product Scenario: Invalid Category"));

        return productRepository.findByCategory(category).stream().map((Product product)-> {
            ProductDetailsDTO result = modelMapper.map(product,ProductDetailsDTO.class);
            result.setCategoryId(product.getCategory().getId());
            result.setCategoryName(product.getCategory().getCategoryName());
            return result;
        }).toList();
    }
}
