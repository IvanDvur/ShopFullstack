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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes("cart")
public class MainPageController {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;
    private CartRepository cartRepository;
    private static String baseUrl;

    /**
     * Внедрение зависимостей
     * @param categoryRepository репозиторий категорий
     * @param productRepository репозиторий товаров
     * @param clientRepository репозиторий пользователей
     * @param cartRepository корзина
     */
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

    /**
     * Добавляет подкатегории в модель
     * @param model
     */
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
        if(principal ==null){
            return null;
        }
        String username = principal.getName();
        Client client = clientRepository.findByUsername(username);
        return client;
    }

    @ModelAttribute(name = "cart")
    public Cart addCartToModel() {
        return new Cart();
    }

    @GetMapping(value = {"/{sex}", "/{sex}/{type}", ""})
    public String filterBySex(@PathVariable(value = "sex", required = false) String sex,
                              @PathVariable(value = "type", required = false) String type,
                              Model model) {
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

    @PostMapping("/add/{itemId}")
    public String addProductToCart(@PathVariable(name = "itemId") Long id,
                                   @ModelAttribute("cart") Cart cart,
                                   RedirectAttributes attributes) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            cart.addProduct(product.get());
        }
        else {
            return null;
        }
        log.info("Processing cart {}", cart);
        return "redirect:"+baseUrl;
    }

    private Iterable<Category> filterByClothesType(List<Category> categories, ClothesType type) {
        return categories.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
