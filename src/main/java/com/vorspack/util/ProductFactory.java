package com.vorspack.util;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.service.ImageService;
import com.vorspack.service.serviceImpl.FileServiceImpl;
import com.vorspack.service.serviceImpl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

@Component
public class ProductFactory {
    @Autowired
    private ImageService imageService;
    public static Product createProduct() {
        FileServiceImpl fileService = new FileServiceImpl();
        Product product = new Product();
        product.setAsin("test");
        try {
            product.setImage(ImageIO.read(new URL("https://images-cn.ssl-images-amazon.com/images/I/61E4HI-IZ3L._SL1000_.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setLink("link");
        product.setBrand("brand");
        product.setSeller("Amazon.com");
        product.setSellerType(SellerType.AMAZON);
        product.setIfFBA(true);
        product.setRate(4.8f);
        product.setReviewNum(4800);
        product.setPrice("$12.99");
        product.setRankInfo("rankInfo");
        product.setQaNum(40);
        return product;
    }
}
