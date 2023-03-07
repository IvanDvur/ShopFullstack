package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.Cart;
import com.ivand.shopfullstack.model.Client;
import com.ivand.shopfullstack.repository.CartRepository;
import com.ivand.shopfullstack.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    private CartRepository cartRepository;
    private ClientRepository clientRepository;

    public CartController(CartRepository cartRepository, ClientRepository clientRepository) {
        this.cartRepository = cartRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String showCartPage(){
        return "cart";
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

    public void deleteProductFromCart(){

    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("cart") Cart cart, Errors errors, SessionStatus sessionStatus,
                               @AuthenticationPrincipal Client client){
        if(errors.hasErrors()){
            return "cart";
        }
        cart.setClient(client);
        log.info("Processing order {}",cart);
        cartRepository.save(cart);
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
