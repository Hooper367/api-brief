package com.apibrief3.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @ManyToOne
    private Color color;

    @ManyToOne
    private Size size;

    private Float price;

    @OneToOne
    private Promotion promotion;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Avis> avis;
}
