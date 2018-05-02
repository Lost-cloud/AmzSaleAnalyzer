package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Link;
import com.vorspack.service.LinkListService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkListServiceImpl implements LinkListService {
    @Override
    public List<String> createLinkList(Document document) {
        Elements linkElements = document.getElementsByClass(LINK_CLASS);
        List<String> links=new ArrayList<>();
        if (linkElements != null) {
            links=linkElements.eachAttr("href");
        }
        return links;
    }
}
