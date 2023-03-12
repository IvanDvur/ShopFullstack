package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.*;
import com.ivand.shopfullstack.service.AdminService;
import com.ivand.shopfullstack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    private ProductService productService;

    @Autowired
    public AdminController(AdminService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
    }

    @ModelAttribute
    public void addTypesToModel(Model model) {
        productService.addTypesToModel(model);
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
        adminService.addProduct(productForm);
        return "admin";
    }

    @PostMapping("/addCategory")
    public String addNewCategory(CategoryForm categoryForm){
        adminService.addCategory(categoryForm);
        return "admin";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id){
        adminService.deleteProduct(id);
        return "admin";
    }
}
