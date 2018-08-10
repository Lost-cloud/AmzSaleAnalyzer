package com.vorspack.service;

import java.io.InputStream;

public interface FileService {
    /**
     * 以输入流为参数保存文件
     * @param path 保存路径
     * @param name 保存文件名
     * @param inputStream 要保存的流文件
     */
    void saveFile(String path, String name, InputStream inputStream);

    /**
     * 从下载路径获取文件的输入流，下载路径可以是网络和
     * @param downloadPath 下载路径
     * @return 返回输入流
     */
    InputStream getFileStream(String downloadPath);
}
