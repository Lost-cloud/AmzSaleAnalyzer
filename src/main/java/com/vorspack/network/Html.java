package com.vorspack.network;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface Html {
    /**
     * 根据链接获取页面对应得文档对象，从而可以方便得到页面其它值
     * @param url 链接
     * @return jsoup 文件对象
     * @throws IOException IO异常
     */
    Document getHtmlDocument(String url) throws IOException;



}
