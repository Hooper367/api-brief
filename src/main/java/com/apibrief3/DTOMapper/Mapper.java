package com.apibrief3.DTOMapper;



import com.apibrief3.dto.*;
import com.apibrief3.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Mapper {

    public UserDTO toUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAdresses()
                        .stream()
                        .map(adress -> toAdressDTO(adress, user))
                        .collect(Collectors.toList())
        );
    }

    public UserDTO toUserDTO(User user, Adress adress){
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAdresses()
                        .stream()
                        .filter(element -> !element.getId().equals(adress.getId()))
                        .map(element -> toAdressDTO(element, user))
                        .collect(Collectors.toList())
        );
    }

    public AdressDTO toAdressDTO(Adress adress){
        return new AdressDTO(
                adress.getId(),
                adress.getCity(),
                adress.getStreet(),
                adress.getZipCode(),
                adress.getCountry(),
                toUserDTO(adress.getOwner(), adress)
        );
    }

    public AdressDTO toAdressDTO(Adress adress, User user){

        return new AdressDTO(
                adress.getId(),
                adress.getCity(),
                adress.getStreet(),
                adress.getZipCode(),
                adress.getCountry(),
                !adress.getOwner().getId().equals(user.getId()) ? toUserDTO(adress.getOwner()) : null
        );
    }

    public CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getPromotion() != null ? toPromotionDTO(category.getPromotion()) : null,
                category.getProducts() != null ? category.getProducts()
                        .stream()
                        .map((product -> toProductDTO(product, category.getId())))
                        .collect(Collectors.toList())
                        : null

        );
    }

    public CategoryDTO toCategoryDTO(Category category, Product product) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getPromotion() != null ? toPromotionDTO(category.getPromotion()) : null,
                category.getProducts()
                        .stream()
                        .filter(element -> !element.getId().equals(product.getId()))
                        .map((element -> toProductDTO(element, category.getId())))
                        .collect(Collectors.toList())

        );
    }

    public ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                toColorDTO(product.getColor()),
                toSizeDTO(product.getSize()),
                product.getPrice(),
                product.getPromotion() != null ? toPromotionDTO(product.getPromotion()) : null,
                toCategoryDTO(product.getCategory(), product)

        ) ;
    }
    public ProductDTO toProductDTO(Product product, Integer categoryId) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                toColorDTO(product.getColor()),
                toSizeDTO(product.getSize()),
                product.getPrice(),
                product.getPromotion() != null ? toPromotionDTO(product.getPromotion()) : null,
                !product.getCategory().getId().equals(categoryId) ? toCategoryDTO(product.getCategory()) : null
        );
    }


    public CommandDTO toCommandDTO(Command command) {
        return  new CommandDTO(
                command.getId(),
                command.getDateCommand(),
                toUserDTO(command.getUser()),
                toCommandDetailsDTO(command.getCommandDetails())
        );
    }

    private CommandDetailsDTO toCommandDetailsDTO(CommandDetails commandDetails) {
        return new CommandDetailsDTO(
                commandDetails.getId(),
                toAdressDTO(commandDetails.getBillingAdress()),
                toAdressDTO(commandDetails.getDeliveryAdress()),
                commandDetails.getTotalPrice(),
                commandDetails.getProducts()
                        .stream()
                        .map(this::toCommandDetailsProductsDTO)
                        .collect(Collectors.toList())
                );
    }

    private CommandDetailsProductsDTO toCommandDetailsProductsDTO( CommandDetailsProducts commandDetailsProducts) {
        return  new CommandDetailsProductsDTO(
                commandDetailsProducts.getId(),
                toProductDTO(commandDetailsProducts.getProduct()),
                commandDetailsProducts.getQuantity(),
                commandDetailsProducts.getUnitPrice(),
                commandDetailsProducts.getTotalPrice()
                );
    }

    public AvisDTO toAvisDTO(Avis avis) {
        return new AvisDTO(
                avis.getId(),
                avis.getMessage(),
                avis.getNote(),
                toUserDTO(avis.getUser()),
                toProductDTO(avis.getProduct())
                );
    }

    public ColorDTO toColorDTO(Color color) {
        return new ColorDTO(
                color.getId(),
                color.getNameColor()
        );
    }

    public SizeDTO toSizeDTO(Size size) {
        return new SizeDTO(
                size.getId(),
                size.getSize()
        );
    }
    public StockDTO toStockDTO(Stock stock) {
        return new StockDTO(
                stock.getId(),
                stock.getQuantity(),
                toProductDTO(stock.getProduct())
        );
    }
    public PromotionDTO toPromotionDTO(Promotion promotion) {
        return new PromotionDTO(
                promotion.getId(),
                promotion.getDiscountPercentage()
        );
    }



}

