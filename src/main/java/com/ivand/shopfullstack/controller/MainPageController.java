package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.Cart;
import com.ivand.shopfullstack.model.Category;
import com.ivand.shopfullstack.model.ClothesType;
import com.ivand.shopfullstack.model.Product;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes("cart")
public class MainPageController {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Autowired
    public MainPageController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

//    Добавляет типы
    @ModelAttribute
    public void addTypesToModel(Model model){
        Iterable<Category> categories = categoryRepository.findAll();
        ClothesType[] types = ClothesType.values();

        for(ClothesType type:types){
            model.addAttribute(type.toString().toLowerCase(),filterByClothesType((List<Category>) categories,type));
        }
    }

    @ModelAttribute(name = "products")
    public List<Product> addProductsToModel(){
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }

    @ModelAttribute(name="cart")
    public Cart addCartToModel(){
        return new Cart();
    }

    @GetMapping("/{sex}/{type}")
    public String filterBySex(@PathVariable("sex") String sex, @PathVariable("type") String type,Model model){
        if(type.equals("")){
            List<Product> products = (List<Product>) productRepository.findAll();
            List<Product> productsBySex = products.stream().filter(x->x.getSex().equals(sex)).collect(Collectors.toList());
            model.addAttribute("products",productsBySex);
        }
        return null;
//        TODO: Сделать фильтрацию по полу и типу одежды
    }

    @GetMapping
    public String showMainPage(Model model){
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        log.info("Adding categories to model{}",categories);
        return "index";
    }

    @PostMapping
    public void addProductToCart(){

    }

    private Iterable<Category> filterByClothesType(List<Category> categories, ClothesType type){
        return categories.stream()
                .filter(x->x.getType().equals(type))
                .collect(Collectors.toList());
    }

    private Iterable<Product> filterMaleClothes(List<Product> products){
        return products.stream().filter(x->x.getSex().equals("male")).collect(Collectors.toList());
    }



}
