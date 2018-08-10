package com.vorspack.service;

// TODO: 2018/6/18  完成收益计算
public interface ProductRevenueService {
    double getFbaFee(String asin);
    double getShippingFee(double minLength, double midLength, double maxLength);
    double getTax(double productPrice);
}
