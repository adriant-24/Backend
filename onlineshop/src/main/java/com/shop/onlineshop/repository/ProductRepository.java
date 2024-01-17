package com.shop.onlineshop.repository;

import com.shop.onlineshop.entity.Product;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findByNameContaining(String productName, Pageable pageable);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    @Query("select p from Product p JOIN p.category c where c.productCategoryId = :productCategoryId")
    Page<Product> findByCategoryProductCategoryId(@Param("productCategoryId") Long productCategoryId, Pageable pageable);

}
