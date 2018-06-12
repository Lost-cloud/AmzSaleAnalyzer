package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.network.Html;
import com.vorspack.service.ProductService;
import com.vorspack.util.ProductFactory;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product createProduct(String link) {
        return ProductFactory.createProduct(link);
    }
}
