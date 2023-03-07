package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.*;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @ModelAttribute
    public void addTypesToModel(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        ClothesType[] types = ClothesType.values();
        model.addAttribute("types",types);
        for (ClothesType type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByClothesType((List<Category>) categories, type));
        }
    }
    private Iterable<Category> filterByClothesType(List<Category> categories, ClothesType type) {
        return categories.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
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
    public String addNewProduct(ProductForm productForm){
        productRepository.save(productForm.toProduct(categoryRepository.findByName(productForm.getCategory())));
        return "admin";
    }

    @PostMapping("/addCategory")
    public String addNewCategory(CategoryForm categoryForm){
        Optional<Category> requestedCategory = Optional.ofNullable(categoryRepository.findByName(categoryForm.getName()));
        if(requestedCategory.isEmpty()) {
            categoryRepository.save(categoryForm.toCategory());
        }
        return "admin";
    }
}
