package com.ecommerce.service;

import com.ecommerce.dto.CategoryDetailsDTO;
import com.ecommerce.dto.RegisterCategoryDTO;

import java.util.List;

public interface CategoryService {
    void addCategory(RegisterCategoryDTO registerCategoryDTO);
    List<CategoryDetailsDTO> getAllCategories();
    CategoryDetailsDTO getCategoryById(Long id);
}
