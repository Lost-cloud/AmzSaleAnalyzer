package com.vorspack.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlImpl implements Html {
    @Override
    public Document getHtmlDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
