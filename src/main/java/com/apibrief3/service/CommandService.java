package com.apibrief3.service;

import com.apibrief3.DTOMapper.Mapper;
import com.apibrief3.dto.CommandDTO;
import com.apibrief3.exception.EntityNotFoundException;
import com.apibrief3.model.*;
import com.apibrief3.record.commandRequest.AddCommandRequest;
import com.apibrief3.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        List<CommandDetailsProducts> listItems = new ArrayList<>();

        Command command = Command.builder()
                .dateCommand(LocalDateTime.now())
                .user(user)
                .commandDetails(null)
                .build();

        command = commandRepository.save(command);
        CommandDetails commandDetails = generateDetails(command, billingAdress, deliveryAdress);
        command.setCommandDetails(commandDetails);
        command = commandRepository.save(command);

        for (CommandRow commandRow : request.commandItems()) {
            CommandDetailsProducts commandDetailsProducts = generateCommandDetailsProducts(commandRow, commandDetails);
            commandDetails.setTotalPrice(commandDetails.getTotalPrice() + commandDetailsProducts.getTotalPrice());
        }

        commandDetails.setProducts(listItems);
        commandDetailsRepository.save(commandDetails);

        return eventMapper.toCommandDTO(commandRepository.save(command));
    }

    public CommandDetails generateDetails(Command command, Adress billingAdress, Adress deliveryAdress){
        return commandDetailsRepository.save(CommandDetails.builder()
                .command(command)
                .billingAdress(billingAdress)
                .deliveryAdress(deliveryAdress)
                .totalPrice((float) 0)
                .products(null)
                .build());
    }

    public CommandDetailsProducts generateCommandDetailsProducts(CommandRow commandRow, CommandDetails commandDetails){
        Product product = productRepository.findById(commandRow.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(Product.class, "ID", commandRow.getProductId().toString()));

        Float price = product.getPrice();
        if (product.getPromotion() != null) {
            float percentage = product.getPromotion().getDiscountPercentage().floatValue() / 100;
            price -= price * percentage;
        }

        CommandDetailsProducts commandDetailsProducts = CommandDetailsProducts.builder()
                .commandDetails(commandDetails)
                .product(product)
                .quantity(commandRow.getQuantity())
                .unitPrice(price)
                .totalPrice((float) 0)
                .build();

        commandDetailsProducts.setTotalPrice(commandDetailsProducts.getUnitPrice() * commandDetailsProducts.getQuantity());

        return commandDetailsProductsRepository.save(commandDetailsProducts);
    }
}
