package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.service.ProductListService;
import com.vorspack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("BastSellerPagePls")
public class ProductListServiceOnBs implements ProductListService {
    @Autowired
    private ProductService productService;
    private static final String PREFIX = "https://www.amazon.com";
    @Override
    public List<Product> createProductList(List<String> links) {
        List<Product> productList=new ArrayList<>();
        links.forEach(it->{
            if (it!=null&&!it.isEmpty()) {
                productList.add(productService.createProduct(PREFIX+it));
            }
        });
        return productList;
    }
}
