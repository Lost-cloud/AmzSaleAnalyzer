package com.vorspack.util;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.network.Html;
import com.vorspack.network.HtmlImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

import java.io.IOException;
import java.util.List;

public class ProductFactory {
    private static final int UNKNOWN = 0;
    private static Document document;
    private static String allHtmlText;
    private static Product product;
    public static Product createProduct(String link)  {

        LogTool.getLog().info("createProduct() : "+link);

        init(link);

        //设置链接
        setLink(link);

        //设置产品标题
        setProductTitle();

        //设置品牌
        setBrand();

        //设置价格
        setPrice();

        //设置卖家信息，营业类型
        setSellerInfo();

        //设置卖家类型
        setSellerType();

        //设置排行信息
        setRank();

        //设置星级
        setRate();

        //设置review数量
        setReviewNum();

        //设置review
//        setReviews();

        //设置QA数量
        setQANum();


        return product;
    }

    private static void init(String link) {
        Html html = new HtmlImpl();
        try {
            document = html.getHtmlDocument(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //渲染的html，当无法通过Id找到信息时，用来使用正则表达式查找。
        allHtmlText = document.html();
        product = new Product();
    }

    private static void setLink(String link) {
        product.setLink(link);
    }

    private static void setQANum() {

    }

    private static void setRank() {
        Element salesRank = document.getElementById("SalesRank");
        if (salesRank == null) {
            product.setRankInfo("Rank not found");
        }else product.setRankInfo(salesRank.text());
    }

    private static void setPrice() {
        //产品价格
        Element price = document.getElementById("priceblock_ourprice");
        if (price != null) {
            product.setPrice(price.text());
        }else product.setPrice("Not found");
    }

    private static void setBrand() {
        //产品品牌
        Element brand = document.getElementById("brand");
        if (brand == null) {
            brand = document.getElementById("bylineInfo");
            product.setBrand(brand.text());
        }else {product.setBrand(brand.text());}
    }

    private static void setProductTitle() {
        //产品标题
        product.setProductTitle(document.getElementById("productTitle").text());
    }

    //Todo 修改document.getElementById防止出现null point exception
    private static void setRate(){
        //评分：4.0 out of 5 star
        Element acrPopover= getElementById("acrPopover");
        String rate =null;
        if(acrPopover!=null)rate=acrPopover.attr("title");
        try {
            if (rate!=null){
                product.setRate(Float.parseFloat(RegexTool.getInfo("\\d.\\d",rate)));
            }else product.setRate(-1f);
        } catch (RegexNotMatchException e) {
            product.setRate(-1f);
        }
    }

    private static Element getElementById(String id) {
        return document.getElementById(id);
    }

    private static void setReviewNum(){
        //评论数：201 customer reviews
        Element acrCustomerReviewText=getElementById("acrCustomerReviewText");
        String input=null;
        if(acrCustomerReviewText!=null)input= acrCustomerReviewText.text();
        String reviewNumText;
        if(input!=null) {
            try {
                reviewNumText = RegexTool.getInfo("(\\S+) customer", input, 1).replaceAll(",","");
                product.setReviewNum(Integer.parseInt(reviewNumText));
            } catch (RegexNotMatchException e) {
                product.setReviewNum(-1);
            }
        }
    }

    private static void setReviews(){
        //抓取评论
        String customerReviewUrl;
        try {
            customerReviewUrl = "https://www.amazon.com"+ RegexTool.getInfo("href=[\"{0,1}](\\S*)[\"{0,1}]>See all ", allHtmlText,1);
            LogTool.getLog().info("setReviews() : "+customerReviewUrl);
            product.setReviews(getReviews(customerReviewUrl));
        } catch (RegexNotMatchException e) {
            product.setReviews(null);
        }
    }

    private static void setSellerType() {
        //设置卖家类型：亚马逊自营，第三方跟卖，品牌卖家
        //当存在选择尺寸等情况时，不存在卖家信息，所以放置在一个try-catch 块中。
        //默认值为0；
        String sellerNumText;
        if (product.getSeller().equals("Unknown")){
            setSellerType(UNKNOWN);
        }else {
            try {
                sellerNumText = RegexTool.getInfo("[nN]ew</b> \\S(\\d+)", allHtmlText, 1);
                setSellerType(Integer.parseInt(sellerNumText));
            } catch (RegexNotMatchException e) {
                setSellerType(UNKNOWN);
                e.printStackTrace();
            }
        }
}

    private static void setSellerType(int sellerNum) {
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

    private static void setSellerInfo(){
        //售卖信息抓取
        String shipsMsg = document.getElementById("shipsFromSoldBy_feature_div").text();

        //如果售卖信息不存在，或为空，则设置卖家类型为Unknown并返回
        if (shipsMsg == null||shipsMsg.isEmpty()) {
            product.setSeller("Unknown");
            LogTool.getLog().warn("setSellerInfo() : The shipsMsg == null");
            return;
        }

        //如果品牌为AmazonBasics，设置卖家为Amazon.com
        if (product.getBrand().equals("AmazonBasics")) {
            product.setSeller("amazon.com");
            product.setIfFBA(true);
            return;
        }

        //获取Sold By后的卖家
        String shipMsgFiltered;
        //卖家
        String seller;
        try {
            shipMsgFiltered = RegexTool.getInfo("([sS]old by) ([\\w+\\s*]*)", shipsMsg,2);
            if(shipMsgFiltered.contains("Fulfilled by Amazon")){
                product.setIfFBA(true);
                seller = RegexTool.getInfo("([\\w+\\s*]*)and", shipMsgFiltered, 1);
            }else {
                seller = shipMsgFiltered;
            }
        } catch (RegexNotMatchException e) {
            seller = "Unknown";
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
