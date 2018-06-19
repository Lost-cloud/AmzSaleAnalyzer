package com.vorspack.service.serviceImpl;

import com.vorspack.config.RootConfig;
import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.service.ExcelExportService;
import com.vorspack.util.ProductFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ExcelExportServiceImplTest {
    private String[] cellNames = {"图片","Asin","产品名","链接", "品牌", "卖家","卖家类型", "FBA", "自营", "评分", "上架时间", "review数量", "价格", "类目排名", "销量", "QA数量","变体数量","净利润"};

    @Autowired
    private ExcelExportService excelExportService;

    @Test
    public void testExportExcel() {
        List<Product> list=new ArrayList<>();
        list.add(ProductFactory.createProduct());
        excelExportService.makeWorkBook("D://","Test-2",cellNames,list);
    }
}