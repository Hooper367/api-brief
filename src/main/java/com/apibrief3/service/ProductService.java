package com.apibrief3.service;

import com.apibrief3.DTOMapper.Mapper;

import com.apibrief3.dto.AvisDTO;
import com.apibrief3.dto.ProductDTO;
import com.apibrief3.exception.EntityNotFoundException;
import com.apibrief3.model.*;
import com.apibrief3.record.productRequest.AddAvisRequest;
import com.apibrief3.record.productRequest.AddProductRequest;
import com.apibrief3.record.productRequest.UpdateProductRequest;
import com.apibrief3.repository.*;
import com.apibrief3.utils.PropertyChecker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record ProductService(
        ProductRepository productRepository,
        Mapper eventMapper,
        UserRepository userRepository,
        PropertyChecker propertyChecker,
        CategoryRepository categoryRepository,
        AvisRepository avisRepository,
        ColorRepository colorRepository,
        SizeRepository sizeRepository,

        StockRepository stockRepository
) {


    public List<ProductDTO> getAllProducts() {
                return  productRepository.findAll()
                .stream()
                .map(eventMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProduct(Integer productId) {
        return productRepository.findById(productId).map(eventMapper::toProductDTO)
                 .orElseThrow(() -> new EntityNotFoundException(Product.class, "ID", productId.toString()));
    }

    public ProductDTO addProduct(AddProductRequest request) {

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "ID", request.categoryId().toString()));

        Color color = colorRepository.findById(request.colorId())
                .orElseThrow(() -> new EntityNotFoundException(Color.class, "ID", request.colorId().toString()));

        Size size = sizeRepository.findById(request.sizeId())
                .orElseThrow(() -> new EntityNotFoundException(Size.class, "ID", request.sizeId().toString()));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .color(color)
                .size(size)
                .price(request.price())
                .category(category)
                .build();

        product = productRepository.save(product);

        Stock stock = Stock.builder()
                .product(product)
                .quantity(request.quantity() != null && request.quantity() > 0  ? request.quantity() : 0)
                .build();

        stockRepository.save(stock);
        return eventMapper.toProductDTO(product);
    }

    public ProductDTO updateProduct(Integer productId, UpdateProductRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, "ID", productId.toString()));

        Color color = colorRepository.findById(request.colorId())
                .orElseThrow(() -> new EntityNotFoundException(Color.class, "ID", request.colorId().toString()));

        Size size = sizeRepository.findById(request.sizeId())
                .orElseThrow(() -> new EntityNotFoundException(Size.class, "ID", request.sizeId().toString()));

        if (!propertyChecker.isPropertiesSame(request.name().toLowerCase(), product.getName().toLowerCase())){
            product.setName(request.name());
        }

        if (!propertyChecker.isPropertiesSame(color, product.getColor())){
            product.setColor(color);
        }

        if (!propertyChecker.isPropertiesSame(size, product.getSize())){
            product.setSize(size);
        }

        if (!propertyChecker.isPropertiesSame(request.price(), product.getPrice())){
            product.setPrice(request.price());
        }

        if (!propertyChecker.isPropertiesSame(request.description().toLowerCase(), product.getDescription().toLowerCase())){
            product.setDescription(request.description());
        }

        return eventMapper.toProductDTO(productRepository.save(product));
    }

    public AvisDTO addAvisToProduct(AddAvisRequest request, Integer productId){
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, "ID", productId.toString()));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", request.userId().toString()));

        Avis avis = Avis.builder()
                .product(product)
                .user(user)
                .message(request.message())
                .note(request.note())
                .build();

        return eventMapper.toAvisDTO(avisRepository.save(avis));

    }

}
