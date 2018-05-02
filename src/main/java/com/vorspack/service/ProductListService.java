package com.vorspack.service;

import com.vorspack.domain.Product;

import java.util.List;

public interface ProductListService {
    List<Product> createProductList(List<String> links);
}
