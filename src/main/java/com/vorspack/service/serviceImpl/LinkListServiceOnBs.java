package com.vorspack.service.serviceImpl;

import com.vorspack.network.Html;
import com.vorspack.service.LinkListService;
import com.vorspack.util.LogTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("BestSellerPageLls")
public class LinkListServiceOnBs implements LinkListService {
    @Autowired
    private Html html;

    @Override
    public List<String> createLinkList(String url) {
        Document document;
        List<String> links = new ArrayList<>();
        try {
            document = html.getHtmlDocument(url);
            Elements linkElements = document.getElementsContainingText("id=\"h10-asin");
            LogTool.getLog().info("链接没有获取到"+linkElements.toString());
            if (linkElements != null) {
                links = linkElements.eachAttr("href"); //获取的链接需要添加https://www.amazon.com
            }
            //添加第二页
            links.addAll(Objects.requireNonNull(getNextPageLink(document)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

    private List<String> getNextPageLink(Document document) {
        String nextPageUrl;
        Element nextPageLi = document.selectFirst(".a-last");
        nextPageUrl = nextPageLi.selectFirst("a").attr("href");
        try {
            Document doc = html.getHtmlDocument(nextPageUrl);
            Elements linkElements = doc.getElementsContainingText("id=\"h10-asin-");
            if (linkElements != null) {
                return linkElements.eachAttr("href"); //获取的链接需要添加https://www.amazon.com
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

