package com.vorspack.service;

import java.io.InputStream;

public interface ImageService {

    /**
     *
     * @param path 图片存在的大页面
     * @return 返回具体图片路径
     */
    String createImageUrl(String path);

}
