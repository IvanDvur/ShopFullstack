package com.ivand.shopfullstack.model;

import lombok.Data;


@Data
public class ProductForm {


    private String name;
    private String description;
    private String sex;
    private String price;
    private String oldPrice;
    private Boolean discount = false;
    private Boolean hotProduct = false;
    private String image;
    private String category;

    public Product toProduct(Category category) {
        System.out.println(this.toString());
        return new Product(name, description, sex, Integer.parseInt(price), Integer.parseInt(oldPrice),
                discount, hotProduct, null, category);
    }

    @Override
    public String toString() {
        return "ProductForm{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sex='" + sex + '\'' +
                ", price='" + price + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", discount=" + discount +
                ", hotProduct=" + hotProduct +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
