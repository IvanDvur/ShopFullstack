package com.ivand.shopfullstack.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToOne
    private Client client;

    @NotBlank(message = "Необходимо указать имя адресата")
    private String deliveryName;

    @NotBlank(message = "Необходимо указать улицу доставки")
    private String deliveryStreet;

    @NotBlank(message = "Необходимо указать город доставки")
    private String deliveryCity;

    @NotBlank(message = "Необходимо указать регион доставки")
    private String deliveryState;

    @NotBlank(message = "Необходимо указать индекс доставки")
    private String deliveryZip;

    @CreditCardNumber(message = "Неверный номер карты")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][3-9])$",message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Pattern(regexp = "[0-9]{3}",message = "Неверный CVV код")
    private String ccCVV;

    private Date placedAt=new Date();

    @OneToMany
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        this.products.add(product);
    }

}
