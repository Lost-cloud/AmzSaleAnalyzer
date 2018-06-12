package com.vorspack.domain;

import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.util.RegexTool;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable{
    //品牌
    private String brand;
    //产品标题
    private String productTitle;
    //卖家
    private String seller;
    //价格
    private String price;
    //是否FBA
    private boolean ifFBA;
    //QA数量
    private int qaNum;
    //链接
    private String link;
    //卖家信息
    private SellerType sellerType;
    //评论数量
    private int reviewNum;
    //排名信息
    private String rankInfo;
    //星级
    private float rate;
    //评论
    private ArrayList<String> reviews;
    //首图
    private BufferedInputStream image;

    @Override
    public String toString() {
        return "Brand : "+brand+
                "\nproductTitle : "+productTitle +
                "\nsold by "+seller+" and Seller type is "+sellerType.name()+
                "\nFBA :"+ifFBA+
                "\nprice : "+price+
                "\nRank : "+rankInfo+
                "\nRate : "+rate+
                "\nReview : "+reviewNum;
    }

    public int getQaNum() {
        return qaNum;
    }

    public void setQaNum(int qaNum) {
        this.qaNum = qaNum;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public SellerType getSellerType() {
        return sellerType;
    }

    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isIfFBA() {
        return ifFBA;
    }

    public void setIfFBA(boolean ifFBA) {
        this.ifFBA = ifFBA;
    }

    public int getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(int reviewNum) {
        this.reviewNum = reviewNum;
    }

    public String getRankInfo() {
        return rankInfo;
    }

    public void setRankInfo(String rankInfo) {
        this.rankInfo = rankInfo;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

}
