package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.network.Html;
import com.vorspack.service.FileService;
import com.vorspack.service.ImageService;
import com.vorspack.service.ProductService;
import com.vorspack.util.LogTool;
import com.vorspack.util.RegexTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {

    private static final int UNKNOWN = 0;

    private final Html html;
    private final FileService fileService;
    private final ImageService imageService;

    private Document document;
    private String allHtmlText;
    private Product product;

    @Autowired
    public ProductServiceImpl(Html html, FileService fileService, ImageService imageService) {
        this.html = html;
        this.fileService = fileService;
        this.imageService = imageService;
    }

    /**
     * @param link 输入产品展示页链接
     * @return 返回产品对象
     */
    public Product createProduct(String link) {

        LogTool.getLog().info("createProduct() : " + link);

        init(link);

        //设置链接
        setLink(link);

        //设置产品标题
        setProductTitle();

        //设置品牌
        setBrand();

        //设置价格
        setPrice();

        //设置卖家信息，营业类型
        setSellerInfo();

        //设置卖家类型
        setSellerType();

        //设置排行信息
        setRank();

        //设置星级
        setRate();

        //设置review数量
        setReviewNum();

        //设置review
        setReviewUrl();

        //设置QA数量
        setQANum();

        //设置Asin
        setProductAsin();

        //设置图片
        setProductImage();

        return product;
    }

    private void setProductImage() {
        String imgUrl = imageService.createImageUrl(product.getLink());
        if (imgUrl == null) {
            // TODO: 2018/6/18 设置默认图片
            product.setImage(imageService.createImage(""));
        } else product.setImage(imageService.createImage(""));
    }

    private void setProductAsin() {

    }

    /**
     * 创建产品链接的html对应的文本，以及创建一个product对象
     *
     * @param link 产品展示链接
     */
    private void init(String link) {
        try {
            document = html.getHtmlDocument(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //渲染的html，当无法通过Id找到信息时，用来使用正则表达式查找。
        allHtmlText = document.html();
        product = new Product();
    }

    /**
     * 设置产品的链接
     *
     * @param link 产品链接
     */
    private void setLink(String link) {
        product.setLink(link);
    }

    /**
     * 设置QA数量
     */
    private void setQANum() {

    }

    /**
     * 设置排名
     */
    private void setRank() {
        Element salesRank = document.getElementById("SalesRank");
        if (salesRank == null) {
            product.setRankInfo("Rank not found");
        } else product.setRankInfo(salesRank.text());
    }

    /**
     * 设置价格
     */
    private void setPrice() {
        //产品价格
        Element price = document.getElementById("priceblock_ourprice");
        if (price != null) {
            product.setPrice(price.text());
        } else product.setPrice("Not found");
    }

    /**
     * 设置品牌
     */
    private void setBrand() {
        //产品品牌
        Element brand = document.getElementById("brand");
        if (brand == null) {
            brand = document.getElementById("bylineInfo");
            product.setBrand(brand.text());
        } else {
            product.setBrand(brand.text());
        }
    }

    /**
     * 设置标题
     */
    private void setProductTitle() {
        //产品标题
        product.setProductTitle(document.getElementById("productTitle").text());
    }

    //Todo 修改document.getElementById防止出现null point exception

    /**
     * 设置评价星级
     */
    private void setRate() {
        //评分：4.0 out of 5 star
        Element acrPopover = getElementById("acrPopover");
        String rate = null;
        if (acrPopover != null) rate = acrPopover.attr("title");
        try {
            if (rate != null) {
                product.setRate(Float.parseFloat(RegexTool.getInfo("\\d.\\d", rate)));
            } else product.setRate(-1f);
        } catch (RegexNotMatchException e) {
            product.setRate(-1f);
        }
    }

    /**
     * @param id html 元素id
     * @return 返回jsoup element对象
     */
    private Element getElementById(String id) {
        return document.getElementById(id);
    }

    /**
     * 设置评论数量
     */
    private void setReviewNum() {
        //评论数：201 customer reviews
        Element acrCustomerReviewText = getElementById("acrCustomerReviewText");
        String input = null;
        if (acrCustomerReviewText != null) input = acrCustomerReviewText.text();
        String reviewNumText;
        if (input != null) {
            try {
                reviewNumText = RegexTool.getInfo("(\\S+) customer", input, 1).replaceAll(",", "");
                product.setReviewNum(Integer.parseInt(reviewNumText));
            } catch (RegexNotMatchException e) {
                product.setReviewNum(-1);
            }
        }
    }

    /**
     * 设置评论
     */
    private void setReviewUrl() {
        //抓取评论url
        String customerReviewUrl;
        try {
            customerReviewUrl = "https://www.amazon.com" + RegexTool.getInfo("href=[\"{0,1}](\\S*)[\"{0,1}]>See all ", allHtmlText, 1);
            product.setReviewUrl(customerReviewUrl);
        } catch (RegexNotMatchException e) {
            product.setReviewUrl("Not found");
        }
    }


    /**
     * 设置卖家信息，包括卖家和是否FBA
     */
    private void setSellerInfo() {
        //如果品牌为AmazonBasics，设置卖家为Amazon.com
        if (product.getBrand().equals("AmazonBasics")) {
            product.setSeller("amazon.com");
            product.setIfFBA(true);
            return;
        }
        product.setSeller(getSeller());
    }

    /**
     * 获取卖家名称
     *
     * @return 返回卖家名称
     */
    private String getSeller() {
        //卖家信息元素
        Element merchant_info = document.getElementById("merchant-info");
        //物流信息
        String shipMessage;
        String seller = "Unknown";
        if (merchant_info != null) {
            try {
                shipMessage = merchant_info.text();
                LogTool.getLog().warn(shipMessage);
                //物流FBA
                if (shipMessage.contains("Fulfilled by Amazon")) {
                    product.setIfFBA(true);
                    seller = RegexTool.getInfo("[sS]old by ([\\s\\S\\w]*) and", shipMessage, 1);
                } else if (shipMessage.contains("Amazon.com")) {
                    seller = "amazon.com";
                } else seller = RegexTool.getInfo("[sS]old by ([\\s\\S\\w]*) .", shipMessage, 1);
            } catch (RegexNotMatchException e) {
                e.printStackTrace();
            }
        }
        return seller;
    }

    /**
     * 设置卖家类型
     */
    private void setSellerType() {
        //设置卖家类型：亚马逊自营，第三方跟卖，品牌卖家
        //当存在选择尺寸等情况时，不存在卖家信息，所以放置在一个try-catch 块中。
        //默认值为UNKNOWN==0；

        String sellerNumText;
        try {
            sellerNumText = RegexTool.getInfo("[nN]ew</b> \\S(\\d+)", allHtmlText, 1);
            setSellerType(Integer.parseInt(sellerNumText));
        } catch (RegexNotMatchException e) {
            setSellerType(UNKNOWN);
            e.printStackTrace();
        }

    }

    /**
     * @param sellerNum 卖家的数量
     */
    private void setSellerType(int sellerNum) {
        //抓取不到卖家数时，设置为unknown
        if (sellerNum == 0) {
            product.setSellerType(SellerType.UNKNOWN);
            return;
        }
        if (sellerNum == 1) {
            if (product.getBrand().equals("AmazonBasics")) {
                product.setSellerType(SellerType.AMAZON);
            } else product.setSellerType(SellerType.BRAND_SELLER);
        } else if (2 <= sellerNum) {
            //卖家数1-4，设置为未知
            product.setSellerType(SellerType.OTHER_SELLER);
        }
    }

}
