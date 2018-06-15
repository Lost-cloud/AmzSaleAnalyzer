package com.vorspack.service.serviceImpl;

import com.vorspack.config.RootConfig;
import com.vorspack.service.FileService;
import com.vorspack.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


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
    public void testGetImageUrl() {
        String productLink = "https://www.amazon.com/5-11-RUSH12-Tactical-Backpack-Small/dp/B003HHV0QQ/ref=cm_cr_arp_d_product_top?ie=UTF8";
//        String imgUrl = downloadService.createImage(productLink);
        fileService.saveFile("E://","down2.jpeg",fileService.getFileStream(imageService.createImageUrl(productLink)));
//        System.out.println("AAA  :"+imgUrl);
    }

}