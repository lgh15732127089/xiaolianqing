package com.duoxin.utils;


import com.duoxin.service.TemplateService;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Decoder;

import java.io.*;



public class BaseUtils {

    //模板要存储的路径
    @Value("${templatepath}")
    private static String templatepath;

    //模板要存储的url
    @Value("${templateurl}")
    private static String templateurl;

    //前景图要存储的路径
    @Value("${foregroundpath}")
    private static String foregroundpath;

    @Autowired
    private static TemplateService templateService;


    /**
     * @Description: 这是一个解码工具类
     * @Param: string
     * @return: void
     * @Author: lgh
     * @Date: 2020/3/26
     */
    public static String parse(String imgStr, String path) {
        //Base64解码
        try {
            BASE64Decoder encoder = new BASE64Decoder();
            byte[] respByte;
            respByte = encoder.decodeBuffer(imgStr);
            //生成jpeg图片
            String imgFilePath = path + RenameUtils.rename(".jpg");//新生成的图片

            OutputStream out = new FileOutputStream(imgFilePath);

            out.write(respByte);
            out.flush();
            out.close();

            return imgFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }


    }


}