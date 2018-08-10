package com.vorspack.network;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.vorspack.config.RootConfig;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.util.RegexTool;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class})
public class HtmlImplTest {
    //profitcalcToken
    private String getUrl = "https://sellercentral.amazon.com/hz/fba/profitabilitycalculator/index?lang=en_US";
    private String postUrl="https://sellercentral.amazon.com/fba/profitabilitycalculator/" +
            "productmatches?searchKey=+B071Z2GTW7&language=en_US";

    private String postUrl2 = "https://sellercentral.amazon.com/fba/profitabilitycalculator/getafnfee";

    private String fbaFeeId = "afn-amazon-fulfillment-fees";
    private static final String INPUT_ID = "profitcalcToken";

    //Amazon Fulfillment：
    //afn-pricing 商品价格，需要填
    //afn-shipping 亚马逊运费
    //afn-total-revenue 总花费
    //afn-selling-fees  亚马逊销售费用
    //afn-amazon-fulfillment-fees   亚马逊操作费
    //afn-fees-inbound-delivery  运输到亚马逊费用，需要填
    //afn-fulfillment-cost   亚马逊操作费+头程运费
    //afn-seller-proceeds   除商品成本外的利润
    //afn-cost-of-goods     商品花费,需要填
    //afn-net-profit
    //afn-net-margin

    //Your Fulfillment ：
    //mfn-pricing  价格
    //mfn-shipping 运费
    //mfn-total-revenue 运费和价格总和
    //mfn-selling-fees 在亚马逊销售的费用
    //mfn-fulfillment-cost-box 自己运输的花费
    //mfn-fulfillment-cost-total 总花费
    //mfn-seller-proceeds  除商品花费的利润
    //mfn-cost-of-goods    商品花费，需要填
    //mfn-net-profit
    //mfn-net-margin


    @Autowired
    private Html html;

    @Test
    public void post() {
        String profitcalcToken = "not found";
        String postUrl = this.postUrl+"&"+INPUT_ID+"=";
        StringBuffer sb=new StringBuffer(postUrl);

        Map<String,String>  cookies;
        Connection.Response rs;
        Document getDoc;
        Document  postDoc;
        String allHtml = "";
        try {
//            rs = html.getResponse(getUrl);
//            cookies=rs.cookies();
//            getDoc = rs.parse();
//            allHtml = getDoc.html();
//            profitcalcToken = RegexTool.getInfo("name="+"\""+INPUT_ID+"\""+"\\s+value=\"(\\w+)\"", allHtml,1);
//            System.out.println("output is "+profitcalcToken);
//            sb.append("&").append(INPUT_ID).append("=").append(profitcalcToken);
//            System.out.println(html.getDocument(sb.toString()).body());


//            postDoc=html.post(postUrl2,"profitcalcToken",profitcalcToken,cookies);
//            postDoc = html.post(postUrl2 + "?" + "profitcalcToken" + "=" + profitcalcToken);

//            System.out.println(postDoc.body());

            WebClient webClient = new WebClient();
            webClient.setJavaScriptTimeout(10000);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            //去拿网页
            HtmlPage htmlPage = webClient.getPage(getUrl);
            //得到表单
            DomElement form = htmlPage.getElementById("search-form");
            int count= 0;
            int innerCount=0;
            HtmlSubmitInput searchBtn = null;
            HtmlTextInput inputText = null;
            for (DomElement domElement : form.getChildElements()) {
                if(count==1) inputText= (HtmlTextInput) domElement;
                if (count == 2) searchBtn = (HtmlSubmitInput) domElement.getFirstElementChild();
                count++;
            }

            assert inputText != null;
            inputText.setValueAttribute(" B071Z2GTW7");
            assert searchBtn != null;
            HtmlPage searchResultPage = searchBtn.click();
//            //得到提交按钮
            System.out.println("输出的界面是"+searchResultPage);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}