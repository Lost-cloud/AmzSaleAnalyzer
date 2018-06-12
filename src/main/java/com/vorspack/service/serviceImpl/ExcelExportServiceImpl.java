package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.service.ExcelExportService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    @Override
    public void makeWorkBook(String bookTitle, String[] cellNames, List<Product> productList) {

        // 声明一个工作薄
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet(bookTitle);
        sheet.setDefaultColumnWidth((short)15);

        //生成一个样式
        HSSFCellStyle style = hwb.createCellStyle();
        HSSFRow row = sheet.createRow(0);
        //居中
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFCell cell;

        //创建单元格标题
        int size=0;
        for (String cellName : cellNames) {
            cell = row.createCell(size);
            cell.setCellValue(cellName);
            cell.setCellStyle(style);
            size++;
        }

        //根据产品列表去创建单元格数据数组的集合
        List<String[]> cellValueList=createCellValueList(productList,cellNames.length);

        //添加单元格数据
        int rowNum=0;
        for (String[] productInfo : cellValueList) {
            rowNum++;
            HSSFRow valueRow = sheet.createRow(rowNum);
            for (int cNum=0;cNum<cellNames.length;cNum++) {
                valueRow.createCell(cNum).setCellValue(productInfo[cNum]);
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream("E://"+bookTitle+".xls");
            hwb.write(fos);
            fos.close();
            JOptionPane.showMessageDialog(null,"导出成功");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"导出失败");
            e.printStackTrace();
        }

    }

    // String[] cellNames={"链接","品牌","卖家","FBA","自营","评分","上架时间","review数量","价格","类目排名","销量","QA数量"};
    private List<String[]> createCellValueList(List<Product> productList, int length) {
        List<String[]> list = new ArrayList<>();
        for (Product product : productList) {
            String[] cellValues=new String[length];
            cellValues[0] = product.getLink();
            cellValues[1] = product.getBrand();
            cellValues[2] = SellerType.getSellerTypeInfo(product.getSellerType().name());

            if (product.isIfFBA()) {
                cellValues[3] = "是";
            } else {
                cellValues[3] = "否";
            }

            if (product.getSeller().equals("amazon.com")) {
                cellValues[4] = "是";
            }else  cellValues[4] = "否";

            cellValues[5] = String.valueOf(product.getRate());
            cellValues[6] = "手动查询";
            cellValues[7] = String.valueOf(product.getReviewNum());
            cellValues[8] = product.getPrice();
            cellValues[9] = product.getRankInfo();
            cellValues[10] = "手动查询";
            cellValues[11] = String.valueOf(product.getQaNum());
            list.add(cellValues);
        }
        return list;
    }
}
