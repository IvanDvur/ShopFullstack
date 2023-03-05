package com.ivand.shopfullstack;

import com.ivand.shopfullstack.model.Category;
import com.ivand.shopfullstack.model.ClothesType;
import com.ivand.shopfullstack.model.Product;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("login").setViewName("login");
    }

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Autowired
    public WebConfig(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }



    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {
            Category sneakers = new Category("Sneakers",ClothesType.SHOES);
            Category loafers = new Category("Loafers",ClothesType.SHOES);
            Category crocs = new Category("Crocs",ClothesType.SHOES);
            Category cardigan = new Category("Cardigan",ClothesType.OUTERWEAR);
            Category jackets = new Category("Jackets",ClothesType.OUTERWEAR);
            Category anoraks = new Category("Anoraks",ClothesType.OUTERWEAR);
            categoryRepository.save(sneakers);
            categoryRepository.save(loafers);
            categoryRepository.save(crocs);
            categoryRepository.save(cardigan);
            categoryRepository.save(jackets);
            categoryRepository.save(anoraks);

            Product sneaker1 = new Product();
            sneaker1.setCategory(sneakers);
            sneaker1.setName("Sneaker1");
            sneaker1.setDescription("An amazing sneakers");
            sneaker1.setSex("male");

            Product loafer1 = new Product();
            loafer1.setCategory(loafers);
            loafer1.setName("Loafer1");
            loafer1.setDescription("An amazing loafers");

            Product crocs1  = new Product();
            crocs1.setCategory(crocs);
            crocs1.setName("Crocs1");
            crocs1.setDescription("An amazing crocs");
            crocs1.setSex("female");

            productRepository.save(sneaker1);
            productRepository.save(loafer1);
            productRepository.save(crocs1);
        };
    }
}
