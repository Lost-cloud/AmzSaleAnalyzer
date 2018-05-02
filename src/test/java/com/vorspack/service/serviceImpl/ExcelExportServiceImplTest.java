package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.service.ExcelExportService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ExcelExportServiceImplTest {
    private String[] cellNames={"链接","品牌","卖家","FBA","自营","评分","上架时间","review数量","价格","类目排名","销量","QA数量"};
    @Test
    public void testExportExcel() {
        Product product = new Product();
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
        List<Product> list=new ArrayList<>();
        list.add(product);
        ExcelExportService exportService=new ExcelExportServiceImpl();
        exportService.makeWorkBook("Test",cellNames,list);
    }
}