package com.micropods.product_service.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.micropods.product_service.BaseTest;
import com.micropods.product_service.dto.ProductResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest extends BaseTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllProducts() throws Exception {
        ProductResponse product1 = ProductResponse.builder()
                .id("12412")
                .name("Iphone")
                .description("good phone")
                .price(BigDecimal.valueOf(124123))
                .build();

        ProductResponse product2 = ProductResponse.builder()
                .id("15412")
                .name("Iphon1e")
                .description("good phone")
                .price(BigDecimal.valueOf(124123))
                .build();

        List<ProductResponse> productList = Arrays.asList(product1, product2);

        Mockito.when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("12412"))
                .andExpect(jsonPath("$[0].name").value("Iphone"))
                .andExpect(jsonPath("$[0].price").value(124123))
                .andExpect(jsonPath("$[1].id").value("15412"))
                .andExpect(jsonPath("$[1].name").value("Iphon1e"))
                .andExpect(jsonPath("$[1].price").value(124123));
    }
}
