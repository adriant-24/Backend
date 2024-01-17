package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    public ProductCategory findByProductCategoryId(Long productCategoryId);
}
