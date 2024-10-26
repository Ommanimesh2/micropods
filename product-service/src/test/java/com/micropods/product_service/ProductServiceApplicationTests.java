package com.micropods.product_service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropods.product_service.dto.ProductRequest;
import com.micropods.product_service.repository.ProductRepository;
import com.mongodb.assertions.Assertions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests extends BaseTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreateProduct() {
    ProductRequest productRequest = getProductRequest();
    try {
      mockMvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(productRequest))).andExpect(status().isCreated());

    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public ProductRequest getProductRequest() {
    return ProductRequest.builder().name("Iphone").description("good phone").price(BigDecimal.valueOf(124123)).build();
  }
}
