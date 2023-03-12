package com.ivand.shopfullstack.service;

import com.ivand.shopfullstack.model.Cart;
import com.ivand.shopfullstack.model.Client;
import com.ivand.shopfullstack.model.Product;
import com.ivand.shopfullstack.repository.CartRepository;
import com.ivand.shopfullstack.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

@Service
@Slf4j
public class CartService {

    CartRepository cartRepository;
    ClientRepository clientRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ClientRepository clientRepository) {
        this.cartRepository = cartRepository;
        this.clientRepository = clientRepository;
    }

    public String deleteProductFromCart(Long id, Cart cart){
        for (Product product: cart.getProducts()) {
            if(product.getId().equals(id)){
                cart.getProducts().remove(product);
                break;
            }
        }
        return "redirect:/cart";
    }

    public String processOrder(Cart cart, Errors errors, SessionStatus sessionStatus, Client client){
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
