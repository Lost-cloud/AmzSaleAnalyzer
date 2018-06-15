package com.vorspack.service.serviceImpl;

import com.vorspack.service.FileService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public void saveFile(String path, String name, InputStream input) {
        //缓冲的字节
        byte[] b=new byte[1024];
        //读取字节长度
        int len;
        //开启文件缓冲流
        BufferedOutputStream bos =null;
        try {
            //输入保存路径和名称
            bos= new BufferedOutputStream(new FileOutputStream(path + name));
            while(input!=null&&(len=input.read(b))!=-1) bos.write(b,0,len);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert input != null&&bos != null;
                input.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过链接获得文件的缓冲输入流
     * @param downloadPath 文件的下载链接
     * @return 返回缓冲输入流
     */
    @Override
    public InputStream getFileStream(String downloadPath) {
        InputStream in=null;
        try {
            URL imageUrl=new URL(downloadPath);
            URLConnection connection = imageUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert in != null;
        return new BufferedInputStream(in);
    }
}
