package com.apibrief3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandDetailsProducts {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private CommandDetails commandDetails;

    @OneToOne
    private Product product;

    private Integer quantity;

    private Float unitPrice;

    private Float totalPrice;
}
