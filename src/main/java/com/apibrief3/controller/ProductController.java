package com.apibrief3.controller;


import com.apibrief3.dto.AvisDTO;
import com.apibrief3.dto.ProductDTO;
import com.apibrief3.exception.ValidationException;
import com.apibrief3.record.productRequest.AddAvisRequest;
import com.apibrief3.record.productRequest.AddProductRequest;
import com.apibrief3.record.productRequest.UpdateProductRequest;
import com.apibrief3.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/products")
public record ProductController(ProductService productService) {

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer productId){
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody AddProductRequest addProductRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(addProductRequest));
    }
    @PostMapping("/{productId}/avis")
    public ResponseEntity<AvisDTO> addAvis(@PathVariable Integer productId, @Valid @RequestBody AddAvisRequest addAvisRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addAvisToProduct(addAvisRequest, productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Integer productId,
            @Valid @RequestBody UpdateProductRequest updateProductRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) throw new ValidationException(bindingResult.getFieldErrors());
        return ResponseEntity.ok().body(productService.updateProduct(productId, updateProductRequest));
    }


}
