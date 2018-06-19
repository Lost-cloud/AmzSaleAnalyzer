package com.vorspack.service;

import com.vorspack.domain.Product;

import java.util.List;

public interface ProductValueListService {
    List<Object[]> createProductIValueList(List<Product> products);
}
