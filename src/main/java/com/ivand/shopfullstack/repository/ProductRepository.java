package com.ivand.shopfullstack.repository;


import com.ivand.shopfullstack.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findBySex(String sex);

    List<Product> findBySexAndCategoryName(String sex, String categoryName);

}
