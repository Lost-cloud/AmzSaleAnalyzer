package com.vorspack.service;

import com.vorspack.domain.Product;

import java.util.List;

public interface ProductListService {
    /**
     * 创建产品列表
     * @param links 输入搜索结果页面产品链接列表
     * @return 产品列表
     */
    List<Product> createProductList(List<String> links);
}
