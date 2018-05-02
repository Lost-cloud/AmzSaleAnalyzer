package com.vorspack.service;

import com.vorspack.domain.Product;
import java.util.List;

public interface ExcelExportService {
    void makeWorkBook(String bookTitle, String[] cellNames, List<Product> productList);
}
