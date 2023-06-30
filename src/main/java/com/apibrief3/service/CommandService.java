package com.apibrief3.service;

import com.apibrief3.DTOMapper.Mapper;
import com.apibrief3.dto.CommandDTO;
import com.apibrief3.exception.EntityNotFoundException;
import com.apibrief3.model.*;
import com.apibrief3.record.commandRequest.AddCommandRequest;
import com.apibrief3.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public record CommandService(
        CommandRepository commandRepository,

        ProductRepository productRepository,

        UserRepository userRepository,

        Mapper eventMapper,

        AdressRpository adressRpository,

        CommandDetailsProductsRepository commandDetailsProductsRepository,
        CommandDetailsRepository commandDetailsRepository


) {

    public List<CommandDTO> getAllCommands(){
        return commandRepository.findAll()
                .stream()
                .map(eventMapper::toCommandDTO)
                .collect(Collectors.toList());
    }
    public CommandDTO getCommand(Integer commandId) {
        return commandRepository.findById(commandId).map(eventMapper::toCommandDTO)
                .orElseThrow(()-> new EntityNotFoundException(Command.class, "ID", commandId.toString()));
    }

    public CommandDTO addCommand(AddCommandRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", request.userId().toString()));

        Adress billingAdress = adressRpository.findById(request.billingAdressId())
                .orElseThrow(() -> new EntityNotFoundException(Adress.class, "ID", request.billingAdressId().toString()));

        Adress deliveryAdress = adressRpository.findById(request.deliveryAdressId())
                .orElseThrow(() -> new EntityNotFoundException(Adress.class, "ID", request.deliveryAdressId().toString()));

        Command command = Command.builder()
                .dateCommand(LocalDateTime.now())
                .commandDetails(null)
                .user(user)
                .build();
        command = commandRepository.save(command);

        CommandDetails commandDetails = CommandDetails.builder()
                .command(command)
                .billingAdress(billingAdress)
                .deliveryAdress(deliveryAdress)
                .totalPrice(Float.parseFloat("0"))
                .products(null)
                .build();

        commandDetails = commandDetailsRepository.save(commandDetails);

        for (Integer productId :request.productsIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException(Product.class, "ID", productId.toString()));

            CommandDetailsProducts commandDetailsProducts = CommandDetailsProducts.builder()
                    .commandDetails(commandDetails)
                    .product(product)
                    .price(product.getPrice())
                    .build();

            commandDetailsProducts = commandDetailsProductsRepository.save(commandDetailsProducts);
            commandDetails.setTotalPrice(commandDetails.getTotalPrice() + commandDetailsProducts.getPrice());
            commandDetails = commandDetailsRepository.save(commandDetails);
        }
        return eventMapper.toCommandDTO(commandRepository.save(command));
    }


}
