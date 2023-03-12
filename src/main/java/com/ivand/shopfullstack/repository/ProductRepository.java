package com.ivand.shopfullstack.repository;


import com.ivand.shopfullstack.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findBySex(String sex);

    List<Product> findBySexAndCategoryName(String sex, String categoryName);
    Optional<Product> findById(Long id);

    void deleteById(Long id);
}
