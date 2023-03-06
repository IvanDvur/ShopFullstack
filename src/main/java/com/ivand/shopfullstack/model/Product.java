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

    public Product(String name, String description, String sex, Double price, Double oldPrice, Boolean discount,
                   Boolean hotProduct, String image, Category category) {
        this.name = name;
        this.description = description;
        this.sex = sex;
        this.price = price;
        this.oldPrice = oldPrice;
        this.discount = discount;
        this.hotProduct = hotProduct;
        this.image = image;
        this.category = category;
    }
}
