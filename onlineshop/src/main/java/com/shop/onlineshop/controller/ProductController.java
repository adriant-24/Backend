package com.shop.onlineshop.controller;

import com.shop.onlineshop.dto.ProductCategoryDto;
import com.shop.onlineshop.dto.ProductDto;
import com.shop.onlineshop.mapper.ProductMapper;
import com.shop.onlineshop.service.ProductService;
import com.shop.onlineshop.utils.PageableUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    ProductService productService;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/products")
    public ResponseEntity<Map<String,Object>> getProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "productId,asc") String[] sort){


        Page<ProductDto> pageProducts = productService.
                getAllProducts(PageableUtils.createPageable(page,size,sort)).
                map(ProductMapper::productToProductDto);
        return PageableUtils.preparePaginatedResponse("products", pageProducts);
    }

    @GetMapping("/categories")
    public List<ProductCategoryDto> getCategories(){
        List<ProductCategoryDto> list = productService.getAllProductCategories()
                .stream()
                .map(ProductMapper::productCategoryToProductCategoryDto)
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/products/category")
    public ResponseEntity<Map<String,Object>> searchByCategoryId(@RequestParam("id") Long productCategoryId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "productId,asc") String[] sort){

        Page<ProductDto> pageProducts = productService.
                findByProductCategoryId(productCategoryId, PageableUtils.createPageable(page,size,sort)).
                map(ProductMapper::productToProductDto);
        return PageableUtils.preparePaginatedResponse("products", pageProducts);
    }

    @GetMapping("/products/search")
    public ResponseEntity<Map<String,Object>> searchProductByName(@RequestParam("keyword") String  keyword,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "productId,asc") String[] sort){

        Page<ProductDto> pageProducts = productService.
                findByProductName(keyword, PageableUtils.createPageable(page,size,sort)).
                map(ProductMapper::productToProductDto);
        return PageableUtils.preparePaginatedResponse("products", pageProducts);
    }

    @GetMapping("/product")
    public ProductDto getProducts(@RequestParam("id") Long productId){

        return ProductMapper.productToProductDto(productService.getProductById(productId));
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody @Valid  ProductDto productDto){

        return ProductMapper.productToProductDto(productService.save(ProductMapper.productDtoToProduct(productDto)));
    }

    @PutMapping("/products")
    public ProductDto updateProduct(@RequestParam("id")Long productId, @RequestBody ProductDto productDto){

        return ProductMapper.productToProductDto(productService.save(ProductMapper.productDtoToProduct(productDto)));
    }

    @DeleteMapping("/products")
    public void deleteProduct(@RequestParam("id") Long id){
        productService.deleteById(id);
    }
}
