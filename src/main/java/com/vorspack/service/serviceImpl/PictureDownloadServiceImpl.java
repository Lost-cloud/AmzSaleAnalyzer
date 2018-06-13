package com.vorspack.service.serviceImpl;

import com.vorspack.network.Html;
import com.vorspack.network.HtmlImpl;
import com.vorspack.service.PictureDownloadService;
import com.vorspack.util.LogTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Service
public class PictureDownloadServiceImpl implements PictureDownloadService {

    @Autowired
    private Html html;

    @Override
    public String createImage(String downloadPath) {
        try {
            Document document = html.getHtmlDocument(downloadPath);
            Element imgDiv = document.getElementById("detailImg");
//            Elements images = imgDiv.getElementsByTag("img");
            return imgDiv.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "not found";
    }
}
