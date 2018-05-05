package com.vorspack.service;

import com.vorspack.domain.Product;
import java.util.List;

public interface ExcelExportService {
    /**
     * 导出Excel
     * @param bookTitle excel表的名称
     * @param cellNames 单元格名称
     * @param productList 产品列表
     */
    void makeWorkBook(String bookTitle, String[] cellNames, List<Product> productList);
}
