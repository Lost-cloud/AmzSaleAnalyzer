package com.vorspack.service;

import com.vorspack.domain.Product;

import java.util.List;

public interface ProductValueListService {
    /**
     * 创建产品的成员属性列表
     * @param products 产品列表
     * @return 返回所有产品的成员属性数组列表
     */
    List<Object[]> createProductIValueList(List<Product> products);
}
