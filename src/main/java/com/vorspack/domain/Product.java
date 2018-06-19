package com.vorspack.domain;

import java.awt.*;
import java.io.Serializable;

public class Product implements Serializable{

    private String asin;
    //品牌
    private String brand;
    //产品标题
    private String productTitle;
    //卖家
    private String seller;
    //价格
    private String price;
    //链接
    private String link;
    //排名信息
    private String rankInfo;
    //是否FBA
    private boolean ifFBA;
    //评论的链接
    private  String reviewUrl;
    //QA数量
    private int qaNum;
    //评论数量
    private int reviewNum;
    //变体数量
    private  int  variantNum;
    //星级
    private float rate;
    //卖家类型
    private SellerType sellerType;

    //首图
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public int getVariantNum() {
        return variantNum;
    }

    public void setVariantNum(int variantNum) {
        this.variantNum = variantNum;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
