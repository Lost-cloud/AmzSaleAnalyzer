package com.vorspack.repository;

import com.vorspack.config.RootConfig;
import com.vorspack.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Test
    public void insertProduct() {
        Product product = new Product();
        product.setBrand("Test");
        product.setProductTitle("testTitle");
        productRepository.insertProduct(product);
    }
}