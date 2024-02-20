package com.shop.onlineshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.coreutils.exception.ResourceNotFoundException;
import com.shop.onlineshop.dto.ProductDto;
import com.shop.onlineshop.entity.Product;
import com.shop.onlineshop.mapper.ProductMapper;
import com.shop.onlineshop.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts={"/test-sql-files/insertProductCategoryAndProducts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("products",hasSize(3)));
    }

    @Test
    @Order(2)
    void getAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[1].categoryName").value("Mugs"));
    }

    @Test
    @Order(3)
    void getAllProductsByCategory() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "15");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/category").params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("totalItems").value("2"))
                .andExpect(jsonPath("products",hasSize(2)))
                .andExpect(jsonPath("products[0].upc").value("BOOK-TECH-1001"));
    }

  //  @GetMapping("/products/search")
  @Test
  @Order(4)
  void searchProducts() throws Exception {
      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("keyword", "Crash");
      mockMvc.perform(MockMvcRequestBuilders.get("/api/products/search").params(params))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("totalItems").value(1))
              .andExpect(jsonPath("products[0].name").value("Crash Course in Python"));
  }
    //@GetMapping("/product")
    @Test
    @Order(5)
    void getProductById() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1003");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product").params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("description").value("Blue mug!"));
    }

    @Test
    @Order(6)
    void getProductByIdNotFound() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "10");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product").params(params))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value("NOT_FOUND"));
    }
    //@PostMapping("/products")
    @Test
    @Order(7)
    void createNewProduct() throws Exception {
      ProductDto testProduct = ProductDto.builder()
                .category(ProductMapper.productCategoryToProductCategoryDto(
                        productService.getProductCategoryById(16L)))
                .name("Mug Post 3")
                .upc("TEST-MUG-UPC")
                .active(true)
                .description("Test mug post")
                .imageUrl("/test/mug/3")
                .unitPrice(BigDecimal.valueOf(30.3))
                .unitsInStock(15)
                .build();
      assertNotNull(testProduct.getCategory());

      mockMvc.perform(MockMvcRequestBuilders
              .post("/api/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(testProduct)))
              .andExpect(status().is2xxSuccessful())
              .andExpect(jsonPath("name").value("Mug Post 3"));

      List<Product> productsList = productService.findByProductName("Mug Post 3", Pageable.unpaged()).toList();
      assertEquals(productsList.size(),1);
      assertEquals(productsList.get(0).getDescription(),"Test mug post");
    }
    //@PutMapping("/products")
    @Test
    @Order(8)
    void updateProduct() throws Exception {
        ProductDto productDto = ProductMapper.productToProductDto(
                productService.getProductById(1003L));

        productDto.setActive(false);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("active").value(false))
                .andExpect(jsonPath("name").value("Mug 1"));

        List<Product> productsList = productService.findByProductName("Mug 1", Pageable.unpaged()).toList();
        assertEquals(productsList.size(),1);
        assertFalse(productsList.get(0).isActive());
    }
    //@DeleteMapping("/products")
    @Test
    @Order(9)
    void deleteProduct() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1003");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products").params(params))
                .andExpect(status().is2xxSuccessful());
        assertThrows(ResourceNotFoundException.class,
                ()-> productService.getProductById(1003L));
    }

}
