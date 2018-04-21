package com.vorspack.util;

import com.vorspack.domain.Product;
import com.vorspack.network.Html;
import com.vorspack.network.HtmlImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
    public static Product createProduct(Document document) {
        Product product = new Product();
        product.setProductTitle(document.getElementById("productTitle").text());
        Element brand = document.getElementById("brand");
        if (brand == null) {
            brand = document.getElementById("bylineInfo");
            product.setBrand(brand.text());
        }else {product.setBrand(brand.text());}
        product.setPrice(document.getElementById("priceblock_ourprice").text());

        //售卖信息
        String shipsMsg = document.getElementById("shipsFromSoldBy_feature_div").text();
        String  shipMsgFiltered= RegexTool.getInfo("([sS]old by) ([\\w+\\s*]*)", shipsMsg,2);
        String seller = "";
        if(shipMsgFiltered.contains("Fulfilled by Amazon")){
            product.setIfFBA(true);
            seller = RegexTool.getInfo("([\\w+\\s*]*)and", shipMsgFiltered, 1);
        }else {
            seller = shipMsgFiltered;
        }
        product.setSeller(seller);

        //排行信息
        product.setRankInfo(document.getElementById("SalesRank").text());

        //评分：4.0 out of 5 star
        String rate = document.getElementById("acrPopover").attr("title");
        product.setRate(Float.parseFloat(RegexTool.getInfo("\\d.\\d",rate)));

        //评论：201 customer reviews
        String input=document.getElementById("acrCustomerReviewText").text();
        int reviewNum = Integer.parseInt(RegexTool.getInfo("\\d+", input));
        product.setReviewNum(reviewNum);

        //抓取评论
        String allHtmlText = document.html();
        String customerReviewUrl ="https://www.amazon.com"+RegexTool.getInfo("href=[\"{0,1}](\\S*)[\"{0,1}]>See all "+reviewNum+" reviews", allHtmlText,1);
        LogTool.getLog().info(customerReviewUrl);
        product.setReviews(getReviews(customerReviewUrl));
        return product;
    }

    private static ArrayList<String> getReviews(String customerReviewUrl) {
        Html html = new HtmlImpl();
        List<String> reviews=null;
        try {
            Document document = html.getHtmlDocument(customerReviewUrl);
            Elements reviewElements = document.getElementsByAttributeValue("data-hook", "review-body");
            reviews = reviewElements.eachText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ArrayList<String>) reviews;
    }
}
