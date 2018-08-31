package com.vorspack.service.serviceImpl;

import com.vorspack.config.RootConfig;
import com.vorspack.domain.Review;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.network.Html;
import com.vorspack.service.ReviewService;
import com.vorspack.util.RegexTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private Html html;

    private static final String URL = "https://www.amazon.com/Waterproof-Drawstring-lightweight-Sackpack-backpack/product-reviews/B06XDP8NX8/ref=cm_cr_arp_d_viewopt_srt?" +
            "ie=UTF8&reviewerType=all_reviews&pageNumber=1";
    private Document document;

    private List<Review> reviewList;

    @Test
    public void getReviewInfo() throws RegexNotMatchException {
        Elements reviewDivs = document.getElementsByAttributeValue("data-hook", "review");
        for (Element reviewDiv : reviewDivs) {
            outReviewInfo(reviewDiv);
        }
        reviewList.sort((o1, o2) -> {
            if (o1.getReviewDate().isAfter(o2.getReviewDate()))
                return 1;
            else return 0;
        });
        reviewList.forEach(System.out::println);
    }

    private void outReviewInfo(Element reviewDiv) throws RegexNotMatchException {
        Element rate = reviewDiv.selectFirst(".a-link-normal");
        Element author = reviewDiv.selectFirst("[data-hook=review-author]");
        Element time = reviewDiv.selectFirst("[data-hook=review-date]");
        Element vp = reviewDiv.selectFirst("[data-hook=avp-badge]");
        Element content = reviewDiv.selectFirst("[data-hook=review-body]");
        Element style = reviewDiv.selectFirst("[data-hook=format-strip]");

        String rateText = RegexTool.getInfo("([\\d.]+) ", rate.text(), 1);
        String authorText = RegexTool.getInfo("By([\\s\\w]+)", author.text(), 1);
        String timeText = RegexTool.getInfo("on ([\\s\\S]+)", time.text(), 1);
        String vpText = vp.text();
        String contentText = content.text();
        String styleText = RegexTool.getInfo("Color: ([\\s\\w]+)", style.text(), 1);

        Review review=new Review();
        review.setAuthor(authorText);
        review.setContent(contentText);
        review.setRating(Double.parseDouble(rateText));
        review.setStyle(styleText);
        review.setVP(vpText.contains("Verified Purchase"));
        review.setReviewDate(getLocalDate(timeText));
        reviewList.add(review);


        System.out.println("评论信息\n" + rateText + "\n"
                + authorText + "\n"
                + getLocalDate(timeText) + "\n"
                + vpText + "\n"
                + styleText + "\n"
                + contentText + "\n");

    }

    private LocalDate getLocalDate(String timeText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        return LocalDate.parse(timeText, formatter);
    }

    @Before
    public void init() {
        reviewList=new ArrayList<>();
        try {
            document = html.getDocument(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}