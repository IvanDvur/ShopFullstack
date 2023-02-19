package com.ivand.shopfullstack.security;


import com.ivand.shopfullstack.model.Client;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public Client toClient(PasswordEncoder passwordEncoder){
        return new Client(username,passwordEncoder.encode(password),fullname,street,city,state,zip,phone,"USER");
    }
}
