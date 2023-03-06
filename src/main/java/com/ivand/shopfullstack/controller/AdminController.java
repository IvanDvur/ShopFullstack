package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.Category;
import com.ivand.shopfullstack.model.Product;
import com.ivand.shopfullstack.model.ProductForm;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public AdminController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

    }

    @ModelAttribute(name = "newProduct")
    public Product newProduct(){
        return new Product();
    }

    @ModelAttribute(name = "newCategory")
    public Category newCategory(){
        return new Category();
    }

    @GetMapping
    public String getAdminPage(){
        return "admin";
    }

    @PostMapping("/addProduct")
    public String addNewProduct(ProductForm product){
        productRepository.save(product.toProduct());
        return "admin";
    }

    @PostMapping("/addCategory")
    public String addNewCategory(){
        return "admin";
    }
}
