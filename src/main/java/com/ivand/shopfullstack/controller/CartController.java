package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.model.Cart;
import com.ivand.shopfullstack.model.Client;
import com.ivand.shopfullstack.service.CartService;
import com.ivand.shopfullstack.service.ClientService;
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

    private CartService cartService;
    private ClientService clientService;

    public CartController(ClientService clientService, CartService cartService) {
        this.cartService = cartService;
        this.clientService = clientService;
    }

    @GetMapping
    public String showCartPage() {
        return "cart";
    }

    @ModelAttribute(name = "client")
    public Client client(Principal principal) {
        return clientService.resolveClient(principal);
    }

    @PostMapping("/{id}")
    public String deleteProductFromCart(@PathVariable(name = "id") Long id,
                                        @SessionAttribute(name = "cart") Cart cart) {
        return cartService.deleteProductFromCart(id, cart);
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("cart") Cart cart, Errors errors, SessionStatus sessionStatus,
                               @AuthenticationPrincipal Client client) {
        return cartService.processOrder(cart, errors, sessionStatus, client);
    }

}
