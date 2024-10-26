package com.micropods.inventory_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micropods.inventory_service.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory -> InventoryResponse.builder()
                .skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();

    }
}
