package com.apibrief3.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Adress {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer number;

    private String street;

    private String zipCode;

    private String city;

    private String country;

    @ManyToOne
    private User owner;

}
