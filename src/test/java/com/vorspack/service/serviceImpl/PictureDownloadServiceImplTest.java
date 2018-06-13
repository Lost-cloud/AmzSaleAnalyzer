package com.vorspack.service.serviceImpl;

import com.vorspack.config.RootConfig;
import com.vorspack.service.PictureDownloadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class PictureDownloadServiceImplTest {
    @Autowired
    private PictureDownloadService pictureDownloadServiceImpl;
    @Test
    public void testGetImageUrl() {
        String productLink = "https://www.amazon.com/5-11-RUSH12-Tactical-Backpack-Small/dp/B003HHV0QQ/ref=cm_cr_arp_d_product_top?ie=UTF8";
        System.out.println("AAA  :"+pictureDownloadServiceImpl.createImage(productLink));
    }
}