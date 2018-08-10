package com.vorspack.util;

import com.vorspack.exception.RegexNotMatchException;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegexToolTest {

    @Test
    public void getInfo() throws RegexNotMatchException {
        String ss = "201 customer reviews";
        System.out.println(RegexTool.getInfo("\\d+", ss));
        String s1 = "Sold by e4Hats ss and Fulfilled by Amazon. Gift-wrap available. " +
                "Fulfillment by Amazon (FBA) is a service we offer sellers that lets them store their products in Amazon's fulfillment centers, " +
                "and we directly pack, ship, and provide customer service for these products. " +
                "Something we hope you'll especially enjoy: FBA items qualify for FREE Shipping and Amazon Prime. If you're a seller, " +
                "Fulfillment by Amazon can help you increase your sales. We invite you to learn more about Fulfillment by Amazon .";
        String[] info = s1.split("Sold by ");
        String[] groups = RegexTool.getGroups("[sS]old by (\\w*\\s*){0,}.", s1);
        String s2 = "Ships from and sold by EZ Sportzwear.";
        String s3;
        System.out.println(s3=RegexTool.getInfo("[sS]old by ([\\w+\\s*]*)",s1,1));
        System.out.println(RegexTool.getInfo("([\\w+\\s*]*)and",s3,1));
//        for (String group : groups) {
//            System.out.println(group);
//        }
    }

    @Test
    public void testGetReviews() throws RegexNotMatchException {
        String input = "</span></div></div></div></div></div><a id=\"end-reviews\" class=\"a-link-normal\" href=\"#\"></a></div><div " +
                "id=\"reviews-medley-footer\" data-hook=\"reviews-medley-footer\" class=\"a-section\"><div class=\"a-row a-spacing-large\">" +
                "<a data-hook=\"see-all-reviews-link-foot\" class=\"a-link-emphasis a-text-bold\" href=\"/High-Sierra-Deluxe-Trapezoid-Black/product-reviews/B01LQPR72G/ref=cm_cr_dp_d_show_all_btm?" +
                "ie=UTF8&reviewerType=all_reviews\">See all 14 reviews</a></div>";
        String customerReviewUrl = RegexTool.getInfo("href=[\"{0,1}](\\S*)[\"{0,1}]>See all " + 14 + " reviews", input, 1);
        System.out.println("https://www.amazon.com"+customerReviewUrl);
    }

    @Test
    public void testGetShipMessage() {
        String input = "Sold by \n" +
                "                    <a href=\"/gp/help/seller/at-a-glance.html/ref=dp_merchant_link?ie=UTF8&amp;seller=A1VLG17X286KN7&amp;isAmazonFulfilled=1\">SPD & GROUP</a> and \n" +
                "                    <a href=\"/gp/help/customer/display.html?ie=UTF8&amp;ref=dp_fulfillment&amp;nodeId=106096011\" id=\"SSOFpopoverLink\">Fulfilled by Amazon</a>. \n" +
                "                    <span class=\"\"> Gift-wrap available. </span> ";
        try {
            String shipMessage = RegexTool.getInfo("[sS]old by [\\s\\S\\w]* Gift-wrap available", input);
            LogTool.getLog().warn(shipMessage);
            String seller = RegexTool.getInfo(">([\\s\\w^<]*)<", shipMessage,1);
            LogTool.getLog().warn(seller);

        } catch (RegexNotMatchException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetToken() {
        String text = "<input type=\"hidden\" name=\"profitcalcToken\" value=\"TYfKAwG7iF15KYzZJYAj2BTqstg6oj3D\">";
        String token;
        try {
            token = RegexTool.getInfo("profitcalcToken\"([\\s\\w]+)=\"(\\w+)", text,2);
            System.out.println("Token is "+token);
        } catch (RegexNotMatchException e) {
            e.printStackTrace();
        }
    }
}