package com.vorspack.domain;

import java.util.ArrayList;

public class Product {
    private String brand;
    private String productTitle;
    private String seller;
    private String price;
    private String shippingMsg;
    private boolean ifFBA;
    private Integer reviewNum;
    private String rankInfo;
    private float rate;

    private ArrayList<String> reviews;
    private ArrayList<String> features;

    @Override
    public String toString() {
        return "Brand : "+brand+
                "\nproductTitle : "+productTitle +
                "\nsale by "+seller+
                "\nprice : "+price+
                "\nRank : "+rankInfo+
                "\nRate : "+rate+
                "\nReview : "+reviewNum;
    }

    @Override
    public boolean equals(Object obj) {
        Product prod;
        if(obj instanceof Product) {
            prod= (Product) obj;
            return this.reviewNum.equals(prod.reviewNum);
        }
        return false;
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

    public String getShippingMsg() {
        return shippingMsg;
    }

    public void setShippingMsg(String shippingMsg) {
        this.shippingMsg = shippingMsg;
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

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }
}
