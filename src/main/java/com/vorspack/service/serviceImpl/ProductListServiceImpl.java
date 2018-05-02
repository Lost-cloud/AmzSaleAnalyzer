package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.network.Html;
import com.vorspack.service.ProductListService;
import com.vorspack.service.ProductService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductListServiceImpl implements ProductListService {
    @Autowired
    private ProductService productService;
    @Autowired
    private Html html;
    @Override
    public List<Product> createProductList(List<String> links) {
        List<Product> productList=new ArrayList<>();
        Product product=null;
        links.forEach(it->{
            if (!(it.contains("https://www.amazon.com/dp") || it.contains("/gp"))) {
                try {
                    Document document = html.getHtmlDocument(it);
                    productList.add(productService.createProduct(document));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return productList;
    }
}
