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

    @OneToOne
    private Color color;

    @OneToOne
    private Size size;

    private Float price;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Avis> avis;
}
