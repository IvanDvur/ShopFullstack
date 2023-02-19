package com.ivand.shopfullstack.model;


import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String sex;

    private Double price;

    private Double oldPrice;

    private Boolean discount;

    private Boolean hotProduct;

    private String image;

    @OneToOne
    @JoinColumn(name="product_type_id")
    private Category category;



}
