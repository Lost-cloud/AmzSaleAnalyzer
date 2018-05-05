package com.vorspack.service;

import com.vorspack.domain.Link;
import org.jsoup.nodes.Document;

import java.util.List;

public interface LinkListService {
    String LINK_CLASS = "a-link-normal s-access-detail-page  s-color-twister-title-link a-text-normal";

    /**
     * 创建搜索结果页面产品链接的列表
     * @param url 输入搜索页面的url
     * @return 产品链接列表
     */
    List<String>  createLinkList(String url);
}
