package com.vorspack.service.serviceImpl;

import com.vorspack.network.Html;
import com.vorspack.network.HtmlImpl;
import com.vorspack.service.ReviewService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: 2018/6/18  评论放到最后再完善
public class ReviewServiceImpl implements ReviewService {
    /**
     * 获取评论
     * @param customerReviewUrl 评论的URl
     * @return 返回评论列表
     */
    private ArrayList<String> getReviews(String customerReviewUrl) {
        Html html = new HtmlImpl();
        List<String> reviews = null;
        try {
            Document document = html.getDocument(customerReviewUrl);
            Elements reviewElements = document.getElementsByAttributeValue("data-hook", "review-body");
            reviews = reviewElements.eachText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ArrayList<String>) reviews;
    }
}
