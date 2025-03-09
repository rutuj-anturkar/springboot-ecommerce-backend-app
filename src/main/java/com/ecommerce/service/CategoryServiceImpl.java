package com.ecommerce.service;

import com.ecommerce.dto.CategoryDetailsDTO;
import com.ecommerce.dto.RegisterCategoryDTO;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addCategory(RegisterCategoryDTO registerCategoryDTO) {
        if(!categoryRepository.findByCategoryName(registerCategoryDTO.getCategoryName()).isEmpty()){
            throw new RuntimeException("Category exists");
        }
        categoryRepository.save(modelMapper.map(registerCategoryDTO, Category.class));
    }

    @Override
    public List<CategoryDetailsDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map((Category category) -> modelMapper.map(category, CategoryDetailsDTO.class))
                .toList();
    }

    @Override
    public CategoryDetailsDTO getCategoryById(Long id){
        return modelMapper.map(categoryRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Category Scenario: Invalid Category Id")), CategoryDetailsDTO.class);
    }
}
