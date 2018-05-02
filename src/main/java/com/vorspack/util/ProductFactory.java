package com.vorspack.util;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.network.Html;
import com.vorspack.network.HtmlImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

import java.io.IOException;
import java.util.List;

public class ProductFactory {
    public static Product createProduct(Document document) {
        //渲染的html，当无法通过Id找到信息时，用来使用正则表达式查找。
        String allHtmlText = document.html();

        Product product = new Product();
        //产品标题
        product.setProductTitle(document.getElementById("productTitle").text());
        //产品品牌
        Element brand = document.getElementById("brand");
        if (brand == null) {
            brand = document.getElementById("bylineInfo");
            product.setBrand(brand.text());
        }else {product.setBrand(brand.text());}

        //产品价格
        product.setPrice(document.getElementById("priceblock_ourprice").text());

        //设置卖家信息
        setSellerInfo(document, product);

        //设置卖家类型
        setSellerType(allHtmlText, product);


        //设置排行信息
        Element salesRank = document.getElementById("SalesRank");
        if (salesRank == null) {
            product.setRankInfo("Rank not found");
        }else product.setRankInfo(salesRank.text());

        //评分：4.0 out of 5 star
        String rate = document.getElementById("acrPopover").attr("title");
        product.setRate(Float.parseFloat(RegexTool.getInfo("\\d.\\d",rate)));

        //评论：201 customer reviews
        String input=document.getElementById("acrCustomerReviewText").text();
        String reviewNumText = RegexTool.getInfo("(\\S+) customer", input, 1).replaceAll(",","");
        int reviewNum = Integer.parseInt(reviewNumText);
        product.setReviewNum(reviewNum);

        //抓取评论
        String customerReviewUrl ="https://www.amazon.com"+RegexTool.getInfo("href=[\"{0,1}](\\S*)[\"{0,1}]>See all ", allHtmlText,1);
        LogTool.getLog().info(customerReviewUrl);
        product.setReviews(getReviews(customerReviewUrl));
        return product;
    }

    private static void setSellerType(String allHtmlText, Product product) {
        //设置卖家类型：亚马逊自营，第三方跟卖，品牌卖家
        //当存在选择尺寸等情况时，不存在卖家信息，所以放置在一个try-catch 块中。
        //默认值为0；
        String sellerNumText="0";
        if (product.getSeller().equals("Unknown")){
            setSellerType(product, Integer.parseInt(sellerNumText));
        }else {
            sellerNumText = RegexTool.getInfo("[nN]ew</b> \\S(\\d)", allHtmlText, 1);
            setSellerType(product, Integer.parseInt(sellerNumText));
        }
    }

    private static void setSellerType(Product product, int sellerNum) {
        //抓取不到卖家数时，设置为unknown
        if (sellerNum == 0) {
            product.setSellerType(SellerType.UNKNOWN);
            return;
        }
        if (sellerNum == 1) {
            if (product.getBrand().equals("AmazonBasics")) {
                product.setSellerType(SellerType.AMAZON);
            }else product.setSellerType(SellerType.BRAND_SELLER);
        } else if (sellerNum<=4) {
            //卖家数1-4，设置为未知
            product.setSellerType(SellerType.UNKNOWN);
        }else {product.setSellerType(SellerType.OTHER_SELLER);}
    }

    private static void setSellerInfo(Document document, Product product) {

        //售卖信息抓取
        String shipsMsg = document.getElementById("shipsFromSoldBy_feature_div").text();
        //如果售卖信息不存在，或为空
        if (shipsMsg == null||shipsMsg.isEmpty()) {
            product.setSeller("Unknown");
            LogTool.getLog().warn("The shipsMsg == null");
            return;
        }
        LogTool.getLog().warn("The shipsMsg == "+shipsMsg);

        //如果品牌为AmazonBasics，设置卖家为Amazon.com
        if (product.getBrand().equals("AmazonBasics")) {
            product.setSeller("Amazon.com");
            product.setIfFBA(true);
            return;
        }

        //获取Sold By后的卖家
        String shipMsgFiltered= RegexTool.getInfo("([sS]old by) ([\\w+\\s*]*)", shipsMsg,2);

        //卖家
        String seller = "";

        if(shipMsgFiltered.contains("Fulfilled by Amazon")){
            product.setIfFBA(true);
            seller = RegexTool.getInfo("([\\w+\\s*]*)and", shipMsgFiltered, 1);
        }else {
            seller = shipMsgFiltered;
        }
        product.setSeller(seller);
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
