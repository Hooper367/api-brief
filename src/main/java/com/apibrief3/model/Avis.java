package com.apibrief3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class Avis {

    @Id
    @GeneratedValue
    private Integer id;

    private String message;

    private Float note;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

}
