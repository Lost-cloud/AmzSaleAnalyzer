package com.vorspack.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable{
    private String brand;
    private String productTitle;
    private String seller;
    private String price;
    private boolean ifFBA;
    private int qaNum;
    private String link;
    private SellerType sellerType;
    private Integer reviewNum;
    private String rankInfo;
    private float rate;
    private ArrayList<String> reviews;
    private ArrayList<String> features;

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

    @Override
    public boolean equals(Object obj) {
        Product prod;
        if(obj instanceof Product) {
            prod= (Product) obj;
            return this.reviewNum.equals(prod.reviewNum);
        }
        return false;
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

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }
}
