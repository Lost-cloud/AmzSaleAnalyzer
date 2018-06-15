package com.vorspack.service;

import java.io.InputStream;

public interface FileService {
    void saveFile(String path, String name, InputStream inputStream);
    InputStream getFileStream(String downloadPath);
}
