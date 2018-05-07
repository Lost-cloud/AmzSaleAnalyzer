package com.vorspack.ui;

import com.vorspack.domain.Product;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.network.Html;
import com.vorspack.service.ExcelExportService;
import com.vorspack.service.LinkListService;
import com.vorspack.service.ProductListService;
import com.vorspack.util.LogTool;
import com.vorspack.util.ProductFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AsaWindow extends JPanel {

    private JTextField textField = new JTextField(20);
    private JTextArea textArea = new JTextArea();
    private Product product;
    private String[] cellNames={"链接","品牌","卖家","FBA","自营","评分","上架时间","review数量","价格","类目排名","销量","QA数量"};

    @Autowired
    private Html html;

    @Autowired
    private ExcelExportService exportService;

    @Autowired
    private LinkListService linkListService;

    @Autowired
    private ProductListService productListService;
    private List<Product> products;


    public AsaWindow() {
        //主界面布局
        setLayout(new BorderLayout());
        //创建并初始化panel
        JPanel panel = new JPanel();
        panel.add(createLabel());
        panel.add(textField);

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

        panel.add(confirmBtn);
        panel.add(clearBtn);
        panel.add(exportBtn);
        panel.add(productLinkBtn);
        panel.add(reviewBtn);
        add(panel, BorderLayout.NORTH);
        add(createScrollPane(), BorderLayout.CENTER);
    }

    private JScrollPane createScrollPane() {
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setAutoscrolls(true);
        return jScrollPane;
    }

    private JLabel createLabel() {
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        JLabel label=new JLabel("网址");
        label.setFont(font);
        return label;
    }

    private void appendText(String text) {
        textArea.append(text+"\n");
    }

    private class ConfirmBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String url = textField.getText();
            if (url.isEmpty()) {
                textArea.append("请输入网址\n");
            } else {
                try {
                    org.jsoup.nodes.Document document = html.getHtmlDocument(url);
                    appendText(document.html());
                    product = ProductFactory.createProduct(url);
                    appendText(product.toString());
                } catch (IOException e1) {
                    textArea.append("网址错误");
                } catch (RegexNotMatchException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class ClearBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
    }

    private class ReviewBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
            ArrayList<String> reviews = product.getReviews();
            LogTool.getLog().info("Empty "+reviews.isEmpty());
            reviews.forEach(it->textArea.append(it+"\n"));
        }
    }

    private class ExportBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            List<Product> list=new ArrayList<>();
////            product.setQaNum(10000);
//            list.add(product);
//            exportService.makeWorkBook("产品分析表格",cellNames,list);
            exportService.makeWorkBook("AmzSaleAnalyzer",cellNames,products);
        }
    }

    private class ProductLinkBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String url = textField.getText();
            if (url.isEmpty()) {
                textArea.append("请输入网址\n");
            } else {
                List<String> links = linkListService.createLinkList(url);
                links.forEach(it -> LogTool.getLog().info(it));
                products = productListService.createProductList(links);
                products.forEach(it->appendText(it.toString()));
            }
        }
    }
}
