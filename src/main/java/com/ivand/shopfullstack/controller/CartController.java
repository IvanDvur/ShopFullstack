package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.Cart;
import com.ivand.shopfullstack.model.Client;
import com.ivand.shopfullstack.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    private CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
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
