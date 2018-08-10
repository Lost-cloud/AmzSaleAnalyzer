package com.vorspack.service.serviceImpl;

import com.vorspack.service.RevenueService;
import org.springframework.stereotype.Service;

@Service
public class RevenueServiceImpl implements RevenueService {

    private static final String CALC_URL = "https://sellercentral.amazon.com/hz/fba/profitabilitycalculator/index?lang=en_US";
    private String postUrl="https://sellercentral.amazon.com/fba/profitabilitycalculator/" +
            "productmatches?searchKey=+B071Z2GTW7&language=en_US";

    private static final String INPUT_ID = "profitcalcToken";

    @Override
    public Double calculateFbaFee(double height, double width, double length) {
        return null;
    }

    @Override
    public Double calculateFbaFee(String asin) {
        return null;
    }

    @Override
    public Double calculateShippingFee(double height, double width, double length) {
        return null;
    }
}
