package com.ivand.shopfullstack.model;

import lombok.Data;

@Data
public class CategoryForm {
    private String name;
    private String type;

    public Category toCategory(){
        System.out.println(this);
        return new Category(name,ClothesType.valueOf(type));
    }

}
