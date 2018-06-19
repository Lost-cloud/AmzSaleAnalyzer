package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.service.ExcelExportService;
import com.vorspack.service.ProductValueListService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    private HSSFWorkbook hwb;
    private HSSFSheet sheet;
    private HSSFRow row_0;

    @Autowired
    private ProductValueListService valueListService;

    @Override
    public void makeWorkBook(String bookTitle, String[] cellNames, List<Product> productList) {
        makeWorkBook("C://", bookTitle, cellNames, productList);
    }

    @Override
    public void makeWorkBook(String path,String bookTitle, String[] cellNames, List<Product> productList) {

        // 声明一个工作薄
        hwb = new HSSFWorkbook();
        sheet = hwb.createSheet(bookTitle);
        sheet.setDefaultColumnWidth((short)15);
        sheet.setColumnWidth(0, (int) (256*14.63+184));

        //创建标题行
        row_0 = sheet.createRow(0);

        //生成一个样式
        HSSFCellStyle style = hwb.createCellStyle();
        //居中
        style.setAlignment(HorizontalAlignment.CENTER);

        //设置标题行
        setCellTitle(cellNames, style);

        //根据产品列表去创建单元格数据数组的集合（除了图片）
        List<Object[]> cellValueList=createCellValueList(productList);

        //添加单元格数据
        int rowNum=0;
        for (Object[] productInfo : cellValueList) {
            rowNum++;
            HSSFRow valueRow = sheet.createRow(rowNum);
            valueRow.setHeight((short) (85*20));
            for (int columnNum=0;columnNum<cellNames.length;columnNum++) {
                if (columnNum == 0) {
                    addImageToCell(rowNum,columnNum,productInfo);
                }else valueRow.createCell(columnNum).setCellValue(String.valueOf(productInfo[columnNum]));
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(path+"//"+bookTitle+".xls");
            hwb.write(fos);
            fos.close();
            JOptionPane.showMessageDialog(null,"导出成功");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"导出失败");
            e.printStackTrace();
        }

    }

    /**
     * 设置首行单元格标题
     * @param cellNames 单元格标题数组
     * @param style 单元格风格
     */
    private void setCellTitle(String[] cellNames, HSSFCellStyle style) {
        HSSFCell cell;
        int size=0;
        for (String cellName : cellNames) {
            cell = row_0.createCell(size);
            cell.setCellValue(cellName);
            cell.setCellStyle(style);
            size++;
        }
    }

    private void addImageToCell(int rowNum, int columnNum, Object[] productInfo) {

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor= new HSSFClientAnchor(0,0,0,0
                        ,(short) columnNum,rowNum, (short) (columnNum+1),rowNum+1);

        BufferedImage bufferImg;
        try {
            bufferImg = (BufferedImage) productInfo[0];
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferImg, "jpeg", byteArrayOut);
            patriarch.createPicture(anchor,hwb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // String[] cellNames={"图片", "Asin", ”产品名“, "链接", "品牌", "卖家", "卖家类型", "FBA", "自营","评分","上架时间","review数量","价格","类目排名","销量","QA数量", "变体数量", "净利润"};

    private List<Object[]> createCellValueList(List<Product> productList) {
        return valueListService.createProductIValueList(productList);
    }

}
