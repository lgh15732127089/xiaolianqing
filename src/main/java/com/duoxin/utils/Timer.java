package com.duoxin.utils;

import com.duoxin.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//这是一个定时器
@Component
public class Timer {

    //用来判断文件是否删除成功
    private static int flag = 1;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private PhotoService photoService;

    //图片要存储的路径
    @Value("${photopath}")
    private String photopath;

    //模板要存储的路径
    @Value("${templatepath}")
    private String templatepath;


    //每天3：05执行清楚照片
    @Scheduled(cron = "0 05 03 ? * *")
    //每隔两秒执行
    //@Scheduled(fixedRate = 2000)
    public void testTasks2() {

        //System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));

        photoService.deleteAll();

        File photoFile = new File(photopath);
        File templateFile = new File(templatepath);
        deleteFile(photoFile);
        deleteFile(templateFile);
        if (flag == 1){
            System.out.println("文件删除成功！");
        }
    }

    private static void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            flag = 0;
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete();
    }
}
