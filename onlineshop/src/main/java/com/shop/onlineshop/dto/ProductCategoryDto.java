package com.shop.onlineshop.dto;

import lombok.*;

import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {

    private Long productCategoryId;

    private String categoryName;

    private Set<ProductDto> productsDto;
}
