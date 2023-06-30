package com.apibrief3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Command command;

    @OneToOne
    private Adress billingAdress;

    @OneToOne
    private Adress deliveryAdress;


    private Float totalPrice;


    @OneToMany(mappedBy = "commandDetails")
    private List<CommandDetailsProducts> products;
}
