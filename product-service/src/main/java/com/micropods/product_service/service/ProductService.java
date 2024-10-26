package com.micropods.product_service.service;

import com.micropods.product_service.dto.ProductRequest;
import com.micropods.product_service.dto.ProductResponse;
import com.micropods.product_service.model.Product;
import com.micropods.product_service.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
    Product product = Product
        .builder()
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .build();

    productRepository.save(product);
    log.info("Product created {} successfully", product.getId());
  }

  public List<ProductResponse> getAllProducts() {
    return productRepository
        .findAll()
        .stream()
        .map(product -> new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()))
        .toList();
  }
}
