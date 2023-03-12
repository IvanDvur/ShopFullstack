package com.ivand.shopfullstack.service;

import com.ivand.shopfullstack.model.Category;
import com.ivand.shopfullstack.model.CategoryForm;
import com.ivand.shopfullstack.model.ProductForm;
import com.ivand.shopfullstack.repository.CategoryRepository;
import com.ivand.shopfullstack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public AdminService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addProduct(ProductForm productForm){
        productRepository.save(productForm.toProduct(categoryRepository.findByName(productForm.getCategory())));
    }

    public void addCategory(CategoryForm categoryForm){
        Optional<Category> requestedCategory = Optional.ofNullable(categoryRepository.findByName(categoryForm.getName()));
        if(requestedCategory.isEmpty()) {
            categoryRepository.save(categoryForm.toCategory());
        }
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
