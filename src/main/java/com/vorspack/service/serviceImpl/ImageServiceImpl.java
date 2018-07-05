package com.vorspack.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vorspack.network.Html;
import com.vorspack.service.FileService;
import com.vorspack.service.ImageService;
import com.vorspack.util.LogTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String LANDING_IMAGE = "landingImage";
    private static final String ATTRIBUTE_KEY = "data-a-dynamic-image";
    //"data-old-hires";
    @Autowired
    private Html html;

    public String createImageUrl(String productLink) {
        //产品页面
        Document document;
        Element imgDiv = null;
        try {
            document = html.getHtmlDocument(productLink);
            //图片的id
            imgDiv = document.getElementById(LANDING_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片的链接
        return getFirstImgUrl(imgDiv);
    }

    @SuppressWarnings("unchecked")
    private String getFirstImgUrl(Element imgDiv) {
        if (imgDiv == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> imgUrls=new ArrayList<>();
        try {
            Map imgUrl_Resolution_map = objectMapper.readValue(imgDiv.attr(ATTRIBUTE_KEY), Map.class);
            imgUrl_Resolution_map.keySet().forEach(it->imgUrls.add((String) it));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgUrls.get(0);
    }


    /**
     * 根据Url或文件路径创建图片
     *
     * @param path 输入图片的路径
     * @return Image对象
     */
    @Override
    public Image createImage(String path) {
        if (path.isEmpty()) LogTool.getLog().warn(path + "is Empty");
        BufferedImage bufferedImage = null;
        try {
            if (path.contains("http")) {
                bufferedImage = ImageIO.read(new URL(path));
            } else bufferedImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

}
