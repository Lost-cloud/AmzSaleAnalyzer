package com.vorspack.service.serviceImpl;

import com.vorspack.network.Html;
import com.vorspack.service.ImageService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Html html;

    public String createImageUrl(String productLink){
        //产品页面
        Document document;
        Element imgDiv=null;
        try {
            document = html.getHtmlDocument(productLink);
            //图片的id
            imgDiv = document.getElementById("landingImage");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片的链接
        if(imgDiv!=null){
            return imgDiv.attr("data-old-hires");
        }else return null;
    }
}
