package com.vorspack.service;

import java.awt.*;

public interface ImageService {

    /**
     *创建图片链接
     * @param path 图片存在的页面
     * @return 返回具体图片路径
     */
    String createImageUrl(String path);

    /**
     * 创建图片
     * @param path 输入图片的路径
     * @return 返回image对象
     */
    Image createImage(String path);

}
