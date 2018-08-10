package com.vorspack.service.serviceImpl;

import com.vorspack.service.ProductRevenueService;
import org.springframework.stereotype.Service;

@Service
public class ProductRevenueServiceImpl implements ProductRevenueService {

    @Override
    public double getFbaFee(String asin) {

        return 0;
    }

    @Override
    public double getShippingFee(double minLength, double midLength, double maxLength) {
        return 0;
    }

    @Override
    public double getTax(double productPrice) {
        return 0;
    }


}
