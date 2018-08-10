package com.vorspack.network;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class HtmlImpl implements Html {

    public static final String FORM_ACTION = "https://sellercentral.amazon.com/fba/profitabilitycalculator/productmatches?" +
            "searchKey=+B071Z2GTW7&language=en_US&profitcalcToken=Tl9xTW904VtRRJfz4XeDghtGXoMj3D";

    /**
     * 通过get 方法得到对应url的jsoup document对象,不需要cookie
     * @param url 网址
     * @return org.jsoup.nodes.Document
     * @throws IOException IO连接异常
     */
    @Override
    public Document getDocument(String url) throws IOException {
        Connection con = getConnection(url);
        return con.get();
    }

    private Connection getConnection(String url) {
        Connection con = Jsoup.connect(url);  // 获取connection
        con.header(USER_AGENT, USER_AGENT_VALUE);   // 配置模拟浏览器
        con.ignoreContentType(true);  //配置content-type
        return con;
    }

    @Override
    public Connection.Response getResponse(String url) throws IOException {
        return getConnection(url).execute();
    }



    @Override
    public Document post(String url, String key, String value,Map<String, String> cookies) throws IOException {
        Connection.Response response = getConnection(url).header("Content-Type","application/json;charset=UTF-8").followRedirects(true)
                .method(Connection.Method.POST).data(key, value).cookies(cookies).execute();
        return response.parse();
    }

    @Override
    public Document post(String url, Map<String, String> data, Map<String, String> cookies) throws IOException {
        Connection.Response response = getConnection(url).ignoreContentType(true).followRedirects(true)
                .method(Connection.Method.POST).data(cookies).cookies(cookies).execute();
        return response.parse();
    }

    @Override
    public Document post(String url) throws IOException {
        return getConnection(url).post();
    }

}
