package com.vorspack.network;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public interface Html {

    String USER_AGENT = "User-Agent";
    String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64;  x64; rv:61.0) Gecko/20100101 Firefox/61.0";

    /**
     * 通过get 方法得到对应url的jsoup document对象
     * @param url 网址
     * @return org.jsoup.nodes.Document
     * @throws IOException IO连接异常
     */
    Document getDocument(String url) throws IOException;
    Document getHtmlDocument(String url) throws IOException;

    Connection.Response getResponse(String url) throws IOException;

    /**
     *post方法提交表单，表单参数只有一个
     * @param url 提交的action
     * @param key 表单参数key
     * @param value 表单参数key对应的值
     * @param cookies cookies
     * @return 返回document对象
     * @throws IOException IOException
     */
    Document post(String url, String key, String value, Map<String,String> cookies) throws IOException;

    /**
     *post方法提交表单，表单参数为Map
     * @param url 提交的action
     * @param data 提交的数据
     * @param cookies cookies
     * @return 返回document对象
     * @throws IOException IOException
     */
    Document post(String url, Map<String, String> data, Map<String, String> cookies) throws IOException;

    Document post(String url) throws IOException;


}
