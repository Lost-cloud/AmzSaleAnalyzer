package com.vorspack.ui;

import com.vorspack.domain.Product;
import com.vorspack.service.ProductValueListService;
import com.vorspack.service.serviceImpl.ProductValueListServiceImpl;
import com.vorspack.util.ProductBasicInfo;
import com.vorspack.util.ProductFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestUi {
    private ProductValueListService valueListService = new ProductValueListServiceImpl();
    private int cellNum = ProductBasicInfo.getCellNames().length;
    private ArrayList<JList> jLists=new ArrayList<>();
    private  ArrayList<ListModel<Object>> listModels=new ArrayList<>();
    public void testUI() {
        //创建一个pane
//        JPanel panel=new JPanel();
//        panel.setLayout(new GridLayout());
        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setLayout(new GridLayout());
        //创建等于cellNum数量的JList
        createJLists();
        //在Pane中添加Jlist
        addJList(scrollPane);
        updateData();
        Show.inFrame(scrollPane, 1000, 1000);

    }

    private void updateData() {
        for (int i = 0; i < cellNum - 1; i++) {
            for (Object[] objects : createProductValues()) {

            }
        }
    }

    private void addJList(JComponent panel) {
        for (JList jList : jLists) {
            panel.add(jList);
        }
    }

    private void createJLists() {
        for (int i = 0; i < cellNum - 1; i++) {
            //创建JList
            JList<Object> jList=new JList<>();
            //为JList添加ListModel
            ListModel<Object> listModel=new DefaultListModel<>();
            //为第一列的JList添加CellRenderer
            if (i==0)jList.setCellRenderer(new ProductCellRenderer());
            //添加到列表中
            //为JList保存对应的listModel
            listModels.add(listModel);
            jLists.add(new JList());
        }
    }

    private JList<Object> createJList() {
        //产品信息
//        Object[] values = createProductValues();
//        outputProduct(values);
        //创建一个列表
        JList<Object> rows = new  JList<>();
        //添加的数据
//        ListModel<Object> listModel = new ProductListModel(values);

//        rows.setModel(listModel);
//        rows.setLayoutOrientation(JList.VERTICAL);
        rows.setCellRenderer(new ProductCellRenderer());
        return rows;
    }


    private List<Object[]> createProductValues() {
        List<Product> products = new ArrayList<>();
        products.add(ProductFactory.createProduct());
        products.add(ProductFactory.createProduct());
        return valueListService.createProductIValueList(products);
    }

    private void outputProduct(Object[] product_values) {
        for (Object product_value : product_values) {
            System.out.println(product_value);
        }
    }

    public static void main(String[] args) {
        TestUi testUi = new TestUi();
        testUi.testUI();
    }
}
