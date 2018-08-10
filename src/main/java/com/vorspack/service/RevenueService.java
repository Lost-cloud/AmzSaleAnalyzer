package com.vorspack.service;

// TODO: 2018/6/18  完成收益计算
public interface RevenueService {

    Double calculateFbaFee(double height, double width, double length);

    Double calculateFbaFee(String asin);

    Double calculateShippingFee(double height, double width, double length);

}
