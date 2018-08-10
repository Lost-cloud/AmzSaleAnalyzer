package com.vorspack.service;

import com.vorspack.domain.Product;

import java.util.List;

public interface ProductValueListService {
    String FIND_BY_YOURSELF = "手动查询";
    String YES_TEXT="是";
    String NO_TEXT="否";
    /**
     * 汇集列表中的产品的所有属性
     * @param products 产品列表
     * @return 返回各产品的属性值
     */
    List<Object[]> createProductIValueList(List<Product> products);
}
