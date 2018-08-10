package com.vorspack.service;

import com.vorspack.domain.Product;
import org.jsoup.nodes.Document;

public interface ProductService {
    /**
     *创建产品
     * @param link 输入产品展示页链接
     * @return 返回Product对象
     */
    Product createProduct(String link);
}
