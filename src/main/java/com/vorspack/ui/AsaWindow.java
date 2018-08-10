package com.vorspack.ui;

import com.vorspack.comparator.ProductComparator;
import com.vorspack.domain.Product;
import com.vorspack.network.Html;
import com.vorspack.service.ExcelExportService;
import com.vorspack.service.LinkListService;
import com.vorspack.service.ProductListService;
import com.vorspack.service.ProductService;
import com.vorspack.util.LogTool;
import com.vorspack.util.ProductBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class AsaWindow extends JPanel {

    private JTextField urlTf = new JTextField(20);
    private JTextArea resultTa = new JTextArea();
    private JTextField excelNameTf =new JTextField(10);
    private JTextField excelDestTf =new JTextField("E:\\Hai\\Excel文件\\抓取数据");

    private Product product;
    private String[] cellNames = {"图片","Asin","产品名","链接", "品牌", "卖家","卖家类型", "FBA", "自营", "评分", "上架时间", "review数量", "价格", "类目排名", "销量","净利润"};

    @Autowired
    private Html html;

    @Autowired
    private ExcelExportService exportService;

    @Autowired
    private LinkListService linkListService;

    @Autowired
    private ProductListService productListService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductComparator productComparator;

    private List<Product> products;


    public AsaWindow() {
        //主界面布局
        setLayout(new BorderLayout());
        //创建并初始化顶部的panel
        JPanel topPanel = new JPanel();
        topPanel.add(createLabel("网址"));
        //添加网址的输入文本域
        topPanel.add(urlTf);
        //添加excel输出路径文本域
        topPanel.add(excelDestTf);
        //添加excel的保存名称
        topPanel.add(excelNameTf);

        //确认按钮
        JButton confirmBtn = new JButton("确认");
        confirmBtn.addActionListener(new ConfirmBtnListener());
        //清空按钮
        JButton clearBtn = new JButton("清空");
        clearBtn.addActionListener(new ClearBtnListener());
        //导出Excel按钮
        JButton exportBtn = new JButton("导出Excel");
        exportBtn.addActionListener(new ExportBtnListener());
        //获取产品链接按钮
        JButton productLinkBtn = new JButton("获取产品链接");
        productLinkBtn.addActionListener(new ProductLinkBtnListener());
        //评论按钮
        JButton reviewBtn = new JButton("评论");
        reviewBtn.addActionListener(new ReviewBtnListener());

        topPanel.add(confirmBtn);
        topPanel.add(clearBtn);
        topPanel.add(exportBtn);
        topPanel.add(productLinkBtn);
        topPanel.add(reviewBtn);
        add(topPanel, BorderLayout.NORTH);
        add(createScrollPane(resultTa), BorderLayout.CENTER);

    }

    private JLabel createImage() {
        Image image = null;
        Image smallImg = null;
        int height, originWidth = 400;
        try {
            image = ImageIO.read(new File("E:\\down.jpeg"));
            height = image.getHeight(null) * originWidth / image.getWidth(null);
            smallImg = image.getScaledInstance(originWidth, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert smallImg != null;
        ImageIcon imageIcon = new ImageIcon(smallImg);
        return new JLabel(imageIcon, JLabel.CENTER);
    }

    private JScrollPane createScrollPane(java.awt.Component view) {
        JScrollPane jScrollPane = new JScrollPane(view);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setAutoscrolls(true);
        return jScrollPane;
    }

    private JLabel createLabel(String name) {
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        JLabel label = new JLabel(name);
        label.setFont(font);
        return label;
    }

    private void appendText(String text) {
        resultTa.append(text + "\n");
    }

    //确定按钮，从单个产品网址获取信息
    private class ConfirmBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String url = urlTf.getText();
            if (url.isEmpty()) {
                resultTa.append("请输入网址\n");
            } else {
                try {
                    org.jsoup.nodes.Document document = html.getDocument(url);
                    appendText(document.html());
                    product = productService.createProduct(url);
                    appendText(product.toString());
                } catch (IOException e1) {
                    resultTa.append("网址错误");
                }
            }
        }
    }

    private class ClearBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultTa.setText("");
        }
    }

    private class ReviewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultTa.setText("");
//            ArrayList<String> reviews = product.getReviews();
//            LogTool.getLog().info("Empty " + reviews.isEmpty());
//            reviews.forEach(it -> resultTa.append(it + "\n"));
        }
    }

    private class ExportBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (products == null) {
                return;
            }
            excelExport();
        }

        private void excelExport() {
            if (excelNameTf.getText().isEmpty() || excelDestTf.getText().isEmpty()) return;
            exportService.makeWorkBook(excelDestTf.getText(), excelNameTf.getText(),ProductBasicInfo.getCellNames(),products);
        }
    }

    private class ProductLinkBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String url = urlTf.getText();
            if (url.isEmpty()) {
                resultTa.append("请输入网址\n");
                return;
            } else {
                List<String> links = linkListService.createLinkList(url);
                links.forEach(it -> LogTool.getLog().info(it));
                products = productListService.createProductList(links);
            }
            products.sort(productComparator);
            products.forEach(it->appendText(it.toString()));
        }
    }
}
