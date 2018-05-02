package com.vorspack.service;

import com.vorspack.domain.Product;
import com.vorspack.util.ProductFactory;
import org.jsoup.nodes.Document;

public class ProductServiceImpl implements ProductService {
    @Override
    public Product createProduct(Document document) {
        return ProductFactory.createProduct(document);
    }
}
