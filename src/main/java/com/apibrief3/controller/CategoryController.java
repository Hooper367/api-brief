package com.apibrief3.controller;

import com.apibrief3.dto.CategoryDTO;
import com.apibrief3.exception.ValidationException;
import com.apibrief3.record.categoryRequest.AddCategoryRequest;
import com.apibrief3.record.categoryRequest.UpdateCategoryRequest;
import com.apibrief3.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/categories")
public record CategoryController(CategoryService categoryService) {
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId){
        return ResponseEntity.ok().body(categoryService.getCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody AddCategoryRequest addCategoryRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(addCategoryRequest));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Integer categoryId,
            @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryId, updateCategoryRequest));
    }
}
