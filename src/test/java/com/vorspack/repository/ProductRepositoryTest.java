package com.vorspack.repository;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.vorspack.config.RootConfig;
import com.vorspack.domain.Product;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.util.LogTool;
import com.vorspack.util.RegexTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ProductRepositoryTest {

    String fbaCalcUrl = "https://sellercentral.amazon.com/hz/fba/profitabilitycalculator/index?lang=en_US";
    String searchInputId = "search-string";
    String asin = "B07C34DYZ3";
    //  get  /fba/profitabilitycalculator/productmatches?searchKey=B07C34DYZ3&language=en_US&profitcalcToken=TYfKAwG7iF15KYzZJYAj2BTqstg6oj3D;
    String searchGetAction = "/fba/profitabilitycalculator/productmatches?searchKey=";
    String token;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertProduct() {
        Product product = new Product();
        product.setBrand("Test");
        product.setProductTitle("testTitle");
        productRepository.insertProduct(product);
    }

    @Test
    public void name() {

        WebClient webClient = initWebClient();
        WebRequest request = initRequest(fbaCalcUrl);
        Set<Cookie> cookies = null;

        try {
            HtmlPage page = webClient.getPage(request);

            //得到cookie
            cookies = webClient.getCookieManager().getCookies();

            token = RegexTool.getInfo("profitcalcToken\"([\\s\\w]+)=\"(\\w+)", page.asXml(), 2);

            System.out.println("Token is " + token);


            HtmlForm searchForm = (HtmlForm) page.getElementById("search-form");
            HtmlSubmitInput submitInput = page.getFirstByXPath("//form[@id='search-form']/span/span/input");

            String newUrl = searchGetAction + asin + "&language=en_US&" + "profitcalcToken=" + token;

            searchForm.setActionAttribute(newUrl);

            //获取输入文本按钮
            HtmlTextInput input = page.getHtmlElementById(searchInputId);
            input.setAttribute("value", asin);

            System.out.println("获取到的是" + submitInput.asXml());

           HtmlPage submitPage= submitInput.click();

            webClient.waitForBackgroundJavaScript(20000);

//            //设置cookie
//            addCookies(webClient, cookies);

            HtmlPage page1 = webClient.getPage(fbaCalcUrl);
            LogTool.getLog().info("PAGE1 : "+page1.asXml());
//            System.out.println("Get result is : " + submitPage.asXml());


            //点击calculate
//            HtmlButton button = page.getHtmlElementById("update-fees-link-announce");
//            button.click();
//            webClient.waitForBackgroundJavaScript(20000);

//            HtmlPage page2 = webClient.getPage(fbaCalcUrl);

//            System.out.println("计算结果是 ："+page2.asXml());
//            getProductInformation(webClient, newUrl);

        } catch (IOException | RegexNotMatchException e) {
            e.printStackTrace();
        }
    }

    private void addCookies(WebClient webClient, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            webClient.getCookieManager().addCookie(cookie);
        }
    }

    private WebRequest initRequest(String link) {
        WebRequest request = null;
        try {
            request = new WebRequest(new URL(link));
            request.setCharset(Charset.forName("utf-8"));
            request.setAdditionalHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:61.0) Gecko/20100101 Firefox/61.0");
            request.setAdditionalHeader("Referer", link);//设置请求报文头里的refer字段
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return request;
    }

    private void getProductInformation(WebClient webClient, String newUrl) throws IOException {
        Page page1 = webClient.getPage("https://sellercentral.amazon.com/" + newUrl);
        WebResponse response = page1.getWebResponse();
        System.out.println("Result is ：" + response.getContentAsString());
    }

    private WebClient initWebClient() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setTimeout(10000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        return webClient;
    }

    @Test
    public void testBaidu() {
        WebClient webClient = initWebClient();
        try {
            HtmlPage htmlPage = webClient.getPage("https://www.baidu.com");
            HtmlSubmitInput input = htmlPage.getHtmlElementById("su");
            HtmlTextInput textInput = htmlPage.getHtmlElementById("kw");
            textInput.setValueAttribute("时间");
            HtmlPage result = input.click();
            System.out.println("Result is " + result.asXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}