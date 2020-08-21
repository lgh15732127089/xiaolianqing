package com.duoxin.utils;

import java.io.*;

public class copyToFile{

    public static void main(String[] args) {
        //源文件地址
        String source="G:\\Java\\IdeaProjects\\xiaolianqiang\\src\\main\\resources\\static\\images\\foreground\\照片模板-8.jpg";
        //目的文件地址
        String dest="G:\\Java\\IdeaProjects\\xiaolianqiang\\src\\main\\resources\\static\\images\\photo\\照片模板-8.jpg";
        CopyImg(source, dest);
    }
    public static void CopyImg(String source,String dest) {
        try {
            //读取源地址文件的字节流
            FileInputStream in=new FileInputStream(source);
            FileOutputStream out=new FileOutputStream(dest);
            byte[]bs=new byte[2048];
            int count=0;
            while ((count=in.read(bs,0,bs.length))!=-1) {
                //把读取到的字节流写入到目的地址的文件里面
                out.write(bs,0,count);

            }
            //刷新下输出流
            out.flush();
            // 关闭输入流和输出流
            out.close();
            out.close();
            System.out.println("复制成功！");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
