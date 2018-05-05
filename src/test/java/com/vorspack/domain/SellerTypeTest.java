package com.vorspack.domain;

import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.util.RegexTool;
import org.junit.Test;

import static org.junit.Assert.*;

public class SellerTypeTest {
    @Test
    public void getSellerTypeInfo() throws RegexNotMatchException {
        System.out.println(SellerType.AMAZON.name());
        System.out.println(SellerType.getSellerTypeInfo(SellerType.AMAZON.name()));
        String test = "2,345 customer";
        String reviewNumText = RegexTool.getInfo("(\\S+) customer", test, 1).replaceAll(",","");
        System.out.println(reviewNumText);
    }
}