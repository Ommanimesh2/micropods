package com.micropods.product_service.controller;

import com.micropods.product_service.dto.ProductRequest;
import com.micropods.product_service.dto.ProductResponse;
import com.micropods.product_service.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createProducts(@RequestBody ProductRequest productRequest) {
    productService.createProduct(productRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }
}
