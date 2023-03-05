package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.*;
import com.ivand.shopfullstack.repository.CartRepository;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ClientRepository;
import com.ivand.shopfullstack.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes("cart")
public class MainPageController {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ClientRepository clientRepository;
    CartRepository cartRepository;
    @Autowired
    public MainPageController(CategoryRepository categoryRepository,
                              ProductRepository productRepository,
                              ClientRepository clientRepository,
                              CartRepository cartRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.cartRepository = cartRepository;
    }

    //    Добавляет типы
    @ModelAttribute
    public void addTypesToModel(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        ClothesType[] types = ClothesType.values();

        for (ClothesType type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByClothesType((List<Category>) categories, type));
        }
    }
    @ModelAttribute(name = "client")
    public Client client(Principal principal){
        String username = principal.getName();
        Client client = clientRepository.findByUsername(username);
        return client;
    }

    @ModelAttribute(name = "products")
    public List<Product> addProductsToModel() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }

    @ModelAttribute(name = "cart")
    public Cart addCartToModel() {
        return new Cart();
    }

    @GetMapping(value = {"/{sex}", "/{sex}/{type}",""})
    public String filterBySex(@PathVariable(value = "sex", required = false) String sex,
                              @PathVariable(value = "type", required = false) String type,
                              Model model) {
        List<Product> filteredProducts;
        String baseUrl = "/main/"+sex;
        model.addAttribute("baseUrl",baseUrl);
        if (sex!=null && type==null) {
            filteredProducts = productRepository.findBySex(sex);
            model.addAttribute("products",filteredProducts);
            return "index";
        } else if (sex!=null && type != null) {
            filteredProducts = productRepository.findBySexAndCategoryName(sex,type);
            model.addAttribute("products",filteredProducts);
            return "index";
        }
        filteredProducts = (List<Product>) productRepository.findAll();
        model.addAttribute("products",filteredProducts);
        return "index";
    }

    @PostMapping("/add")
    public void addProductToCart(Product product, @ModelAttribute("cart") Cart cart, RedirectAttributes attributes) {
        cart.addProduct(product);
        log.info("Processing cart {}",cart);
    }

    private Iterable<Category> filterByClothesType(List<Category> categories, ClothesType type) {
        return categories.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
