package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.*;
import com.ivand.shopfullstack.service.ClientService;
import com.ivand.shopfullstack.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;



@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes("cart")
public class MainPageController {

    private ProductService productService;
    private ClientService clientService;
    private static String baseUrl;

    @Autowired
    public MainPageController(ClientService clientService,
                              ProductService productService) {
        this.productService = productService;
        this.clientService = clientService;
    }

    @ModelAttribute
    public void addTypesToModel(Model model) {
        productService.addTypesToModel(model);
    }

    @ModelAttribute(name = "client")
    public Client resolveClient(Principal principal) {
        return clientService.resolveClient(principal);
    }

    @ModelAttribute(name = "cart")
    public Cart addCartToModel() {
        return new Cart();
    }

    @GetMapping(value = {"/{sex}", "/{sex}/{type}", ""})
    public String filterProducts(@PathVariable(value = "sex", required = false) String sex,
                                 @PathVariable(value = "type", required = false) String type,
                                 Model model) {
        return productService.filterProductsOnPage(sex, type, model, baseUrl);
    }

    @PostMapping("/add/{itemId}")
    public String addProductToCart(@PathVariable(name = "itemId") Long id,
                                   @ModelAttribute("cart") Cart cart,
                                   RedirectAttributes attributes,
                                   Principal principal) {
        log.info("Processing cart {}", cart);
        return productService.addProductToCart(id,principal,cart,baseUrl);
    }
}
