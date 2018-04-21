package com.vorspack.ui;

import com.vorspack.domain.Product;
import com.vorspack.network.Html;
import com.vorspack.network.HtmlImpl;
import com.vorspack.util.ProductFactory;
import com.vorspack.util.RegexTool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class AsaWindow extends JPanel {

    private JTextField textField = new JTextField(20);
    private JTextArea textArea = new JTextArea();
    @Autowired
    private Html html;
    private Product product;

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
        //评论按钮
        JButton reviewBtn = new JButton("评论");
        reviewBtn.addActionListener(new ReviewBtnListener());
        panel.add(confirmBtn);
        panel.add(clearBtn);
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
                    product = ProductFactory.createProduct(document);
                    appendText(product.getProductTitle());
                    appendText(product.getBrand());
                    appendText(product.toString());
//                    String htmlText=html.getHtmlDocument("https://www.amazon.com/" +
//                            "High-Sierra-Deluxe-Trapezoid-Black/product-reviews/B01LQPR72G/" +
//                            "ref=cm_cr_dp_d_show_all_btm?ie=UTF8&reviewerType=all_reviews").html();
//                    textArea.append(htmlText);
                } catch (IOException e1) {
                    textArea.append("网址错误");
                }
            }
        }
    }
    private void appendText(String text) {
        textArea.append(text+"\n");
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
            Logger logger = LogManager.getLogger(AsaWindow.class);
            logger.info("Empty "+reviews.isEmpty());
            reviews.forEach(it->textArea.append(it+"\n"));
        }
    }
}
