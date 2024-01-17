package com.shop.onlineshop.mapper;

import com.shop.onlineshop.dto.ProductCategoryDto;
import com.shop.onlineshop.dto.ProductDto;
import com.shop.onlineshop.entity.Product;
import com.shop.onlineshop.entity.ProductCategory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto productToProductDto(Product product){
        return  productToProductDto(product, true);
    }

    public static List<ProductDto> productListToProductDtoList(List<Product> products){
        return  products.stream().map(ProductMapper::productToProductDto).collect(Collectors.toList());
    }
    public static ProductDto productToProductDto(Product product, boolean mapCategory){

        ProductDto productDto =  ProductDto.builder()
                .productId(product.getProductId())
                .upc(product.getUpc())
                .name(product.getName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .imageUrl(product.getImageUrl())
                .active(product.isActive())
                .unitsInStock(product.getUnitsInStock())
                .dateCreated(product.getDateCreated())
                .lastUpdated(product.getLastUpdated())
                .build();
        if(mapCategory)
            productDto.setCategory(productCategoryToProductCategoryDto(product.getCategory(), false));

        return productDto;
    }

    public static Product productDtoToProduct(ProductDto productDto){
        return  productDtoToProduct(productDto, true);
    }
    public static Product productDtoToProduct(ProductDto productDto, boolean mapCategory){

        Product product = Product.builder()
                .productId(productDto.getProductId())
                .upc(productDto.getUpc())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .unitPrice(productDto.getUnitPrice())
                .imageUrl(productDto.getImageUrl())
                .active(productDto.isActive())
                .unitsInStock(productDto.getUnitsInStock())
                .dateCreated(productDto.getDateCreated())
                .lastUpdated(productDto.getLastUpdated())
                .build();

        if(mapCategory)
            product.setCategory(productCategoryDtoToProductCategory(productDto.getCategory(), false));

        return product;
    }
    public static ProductCategoryDto productCategoryToProductCategoryDto(ProductCategory productCategory){
        return productCategoryToProductCategoryDto(productCategory, false);
    }
    public static ProductCategoryDto productCategoryToProductCategoryDto(ProductCategory productCategory, boolean mapProducts){

        ProductCategoryDto productCategoryDto = ProductCategoryDto.builder()
                .productCategoryId(productCategory.getProductCategoryId())
                .categoryName(productCategory.getCategoryName())
                .build();

        if(mapProducts)
            productCategoryDto.setProductsDto(productsToProductsDto(productCategory.getProducts()));

        return productCategoryDto;
    }

    public static ProductCategory productCategoryDtoToProductCategory(ProductCategoryDto productCategoryDto){
        return productCategoryDtoToProductCategory(productCategoryDto, false);
    }

    public static ProductCategory productCategoryDtoToProductCategory(ProductCategoryDto productCategoryDto, boolean mapProducts){

        ProductCategory productCategory =  ProductCategory.builder()
                .productCategoryId(productCategoryDto.getProductCategoryId())
                .categoryName(productCategoryDto.getCategoryName())
                .build();

        if(mapProducts)
            productCategory.setProducts(productsDtoToProducts(productCategoryDto.getProductsDto()));

        return productCategory;
    }

    public static Set<ProductDto> productsToProductsDto(Set<Product> products){
        return products != null ?
                products.stream()
                        .map(product-> productToProductDto(product,false))
                        .collect(Collectors.toSet()) : null;
    }

    public static Set<Product> productsDtoToProducts(Set<ProductDto> productsDto){
        return productsDto != null ?
                productsDto.stream()
                        .map(productDto-> productDtoToProduct(productDto,false))
                        .collect(Collectors.toSet()) : null;
    }


}
