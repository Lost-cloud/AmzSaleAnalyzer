package com.vorspack.service.serviceImpl;

import com.vorspack.network.Html;
import com.vorspack.service.LinkListService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinkListServiceImpl implements LinkListService {
    @Autowired
    private Html html;

    @Override
    public List<String> createLinkList(String url) {
        Document document ;
        List<String> links=new ArrayList<>();
        try {
            document = html.getDocument(url);
            Elements linkElements = document.getElementsByClass(LINK_CLASS);
            if (linkElements != null) {
                links=linkElements.eachAttr("href");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }
}
