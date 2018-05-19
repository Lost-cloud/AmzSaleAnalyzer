package com.vorspack.repository;

import com.vorspack.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
    int insertProduct(Product product);
}
