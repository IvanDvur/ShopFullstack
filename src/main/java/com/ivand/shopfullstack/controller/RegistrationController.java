package com.ivand.shopfullstack.controller;

import com.ivand.shopfullstack.repository.ClientRepository;
import com.ivand.shopfullstack.security.RegistrationForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registrationForm(){
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm){
        log.info("Processing new user {}",registrationForm.toClient(passwordEncoder));
        clientRepository.save(registrationForm.toClient(passwordEncoder));
        return "redirect:/login";
    }
}
