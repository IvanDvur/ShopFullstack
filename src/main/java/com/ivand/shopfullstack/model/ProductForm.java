package com.ivand.shopfullstack.model;

import lombok.Data;

@Data
public class ProductForm {

    private String name;
    private String description;
    private String sex;
    private String price;
    private String oldPrice;
    private Boolean discount;
    private Boolean hotProduct;
    private String image;
    private Category category;

    public Product toProduct(){
        return new Product(name,description,sex,Double.valueOf(price),Double.valueOf(oldPrice),discount,hotProduct,image,category);
    }
}
