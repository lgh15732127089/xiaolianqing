package com.duoxin.utils;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class RenameUtils {
    //生成一个不重复的名称
    public static String rename(String name){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + String.valueOf(new Random().nextInt(899999) + 100000) + name.substring(name.lastIndexOf("."),name.length());
    }

    //路径截取文件名
    public static String rePath(String path){
        path = path.trim();
        return path.substring(path.lastIndexOf("\\")+1);
    }

}


