package com.shop.onlineshop.service;

import com.shop.onlineshop.entity.Product;
import com.shop.onlineshop.entity.ProductCategory;
import com.shop.onlineshop.exception.ResourceNotFoundException;
import com.shop.onlineshop.repository.ProductCategoryRepository;
import com.shop.onlineshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {

    ProductRepository productRepository;
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository productCategoryRepository){
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public List<ProductCategory> getAllProductCategories(){
        return productCategoryRepository.findAll();
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElseThrow(
                ()-> new ResourceNotFoundException("Product", "productId", String.valueOf(productId))
        );
    }

    public ProductCategory getProductCategoryById(Long productCategoryId){
        return productCategoryRepository.findById(productCategoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Product Category", "productCategoryId", String.valueOf(productCategoryId))
        );
    }

    public Page<Product> findByProductCategoryId(Long id, Pageable pageable){
        return productRepository.findByCategoryProductCategoryId(id, pageable);
    }

    public Page<Product> findByProductName(String productName, Pageable pageable){
        return productRepository.findByNameContaining(productName, pageable);
    }

    @Transactional
    public Product save(Product product)
    {
        return productRepository.save(product);
    }

    @Transactional
    public void delete(Product product){
        productRepository.delete(product);
    }

    @Transactional
    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}
