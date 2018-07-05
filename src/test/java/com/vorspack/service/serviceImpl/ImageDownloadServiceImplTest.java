package com.vorspack.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vorspack.config.RootConfig;
import com.vorspack.service.FileService;
import com.vorspack.service.ImageService;
import com.vorspack.util.LogTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ImageDownloadServiceImplTest {
    @Autowired
    private ImageService downloadService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ImageService imageService;
    @Test
    public void downloadImageUrl() {
        String productLink = "https://www.amazon.com/5-11-RUSH12-Tactical-Backpack-Small/dp/B003HHV0QQ/ref=cm_cr_arp_d_product_top?ie=UTF8";
//        String imgUrl = downloadService.createImage(productLink);
        fileService.saveFile("E://","down2.jpeg",fileService.getFileStream(imageService.createImageUrl(productLink)));
//        System.out.println("AAA  :"+imgUrl);
    }

    @Test
    public void getImgUrls() {
        String productLink = "https://www.amazon.com/Condor-Rip-Away-Pouch-Lite-MultiCam/dp/B00M4ACMX2/ref=sr_1_18/136-6360200-2435627?" +
                "s=sporting-goods&ie=UTF8&qid=1529460924&sr=1-18&keywords=medical+pouch";
        String imgUrl = imageService.createImageUrl(productLink);
        System.out.println(imgUrl);
    }
}