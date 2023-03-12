package com.ivand.shopfullstack.service;

import com.ivand.shopfullstack.model.Cart;
import com.ivand.shopfullstack.model.Category;
import com.ivand.shopfullstack.model.ClothesType;
import com.ivand.shopfullstack.model.Product;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    public void addTypesToModel(Model model){
        Iterable<Category> categories = categoryRepository.findAll();
        ClothesType[] types = ClothesType.values();
        model.addAttribute("types",types);
        for (ClothesType type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByClothesType((List<Category>) categories, type));
        }
    }

    private Iterable<Category> filterByClothesType(List<Category> categories, ClothesType type){
        return categories.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    public String filterProductsOnPage(String sex,String type,Model model,String baseUrl){
        List<Product> filteredProducts;
        baseUrl = "/main/" + sex;
        model.addAttribute("baseUrl", baseUrl);
        if (sex != null && type == null) {
            filteredProducts = productRepository.findBySex(sex);
            model.addAttribute("products", filteredProducts);
            return "index";
        } else if (sex != null && type != null) {
            filteredProducts = productRepository.findBySexAndCategoryName(sex, type);
            model.addAttribute("products", filteredProducts);
            return "index";
        }
        filteredProducts = (List<Product>) productRepository.findAll();
        model.addAttribute("products", filteredProducts);
        return "index";
    }

    public String addProductToCart(Long id, Principal principal, Cart cart,String baseUrl){
        if(principal == null){
            return "redirect:/main" ;
        }
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            cart.addProduct(product.get());

        }
        return "redirect:" + baseUrl;
    }


}
