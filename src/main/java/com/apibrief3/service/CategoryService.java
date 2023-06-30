package com.apibrief3.service;


import com.apibrief3.DTOMapper.Mapper;
import com.apibrief3.dto.CategoryDTO;
import com.apibrief3.exception.EntityAlreadyExistsException;
import com.apibrief3.exception.EntityNotFoundException;
import com.apibrief3.model.Category;
import com.apibrief3.record.categoryRequest.AddCategoryRequest;
import com.apibrief3.record.categoryRequest.UpdateCategoryRequest;
import com.apibrief3.repository.CategoryRepository;
import com.apibrief3.utils.PropertyChecker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record CategoryService(
        CategoryRepository categoryRepository,
        Mapper eventMapper,
        PropertyChecker propertyChecker
) {

    public List<CategoryDTO> getAllCategories() {
        return  categoryRepository.findAll()
                .stream()
                .map(eventMapper::toCategoryDTO)
                .collect(Collectors.toList());

    }
    public CategoryDTO getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId).map(eventMapper::toCategoryDTO)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "ID", categoryId.toString()));
    }

    public CategoryDTO addCategory(AddCategoryRequest request) {

        if(categoryRepository.existsByNameIgnoreCase(request.name())){
            throw new EntityAlreadyExistsException(Category.class, "name", request.name());
        }

        Category category = Category.builder()
                .name(request.name())

                .build();

        return eventMapper.toCategoryDTO(categoryRepository.save(category));
    }
    public CategoryDTO updateCategory(Integer categoryId, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "ID", categoryId.toString()));

        if (!propertyChecker.isPropertiesSame(request.name().toLowerCase(), category.getName().toLowerCase())){

            if(categoryRepository.existsByNameIgnoreCase(request.name())){
                throw new EntityAlreadyExistsException(Category.class, "name", request.name());
            }

            category.setName(request.name());
        }

        return eventMapper.toCategoryDTO(categoryRepository.save(category));
    }
}
