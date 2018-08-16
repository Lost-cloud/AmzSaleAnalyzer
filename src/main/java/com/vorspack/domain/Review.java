package com.vorspack.domain;

import java.time.LocalDate;

public class Review {

    private String productAsin;
    private String content;
    private double rating;
    private String author;
    private String style;
    private boolean isVP;
    private LocalDate reviewDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getProductAsin() {
        return productAsin;
    }

    public void setProductAsin(String productAsin) {
        this.productAsin = productAsin;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isVP() {
        return isVP;
    }

    public void setVP(boolean VP) {
        isVP = VP;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return rating + "\n"
                + author + "\n"
                + reviewDate + "\n"
                + style + "\n"
                + isVP + "\n"
                + content + "\n";

    }
}
