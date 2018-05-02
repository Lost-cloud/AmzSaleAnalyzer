package com.vorspack.service;

import com.vorspack.domain.Link;
import org.jsoup.nodes.Document;

import java.util.List;

public interface LinkListService {
    public static final String LINK_CLASS = "a-link-normal s-access-detail-page  s-color-twister-title-link a-text-normal";
    List<String>  createLinkList(Document document);
}
