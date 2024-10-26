package com.micropods.product_service;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;

public class BaseTest {
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");
    static MockMvc mockMvc;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add(
                "spring.data.mongodb.url",
                mongoDBContainer::getReplicaSetUrl);
    }

    static {
        mongoDBContainer.start();
    }
}
