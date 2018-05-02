package com.vorspack.service;

import com.vorspack.domain.Product;
import org.jsoup.nodes.Document;

public interface ProductService {
    Product createProduct(Document document);
}
