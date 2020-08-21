package com.duoxin.controller;

import com.duoxin.pojo.Photo;
import com.duoxin.service.PhotoService;
import com.duoxin.utils.BaseUtils;
import com.duoxin.utils.RenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    //图片要存储的路径
    @Value("${photopath}")
    private String photopath;

    //图片要存储的url
    @Value("${photourl}")
    private String photourl;

    /**
     * @Description: 上传照片，返回照片是否成功
     * @Param: file
     * @return: String
     * @Author: lgh
     * @Date: 2020/3/16
     */
    @RequestMapping(value = "/upload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String upload(@RequestParam("fileName") MultipartFile file) {

        return null;

    }

    /**
     * @Description: 查询所有照片，返回照片的路径
     * @Param: null
     * @return: list
     * @Author: lgh
     * @Date: 2020/3/16
     */
    @RequestMapping(value = "/select")
    @ResponseBody
    public List<Photo> select() {

        List<Photo> photos = photoService.select();


        return photos;
    }

    /**
     * @Description: 根据前端传入的id，删除后台的数据库照片
     * @Param: Integer
     * @return: null
     * @Author: lgh
     * @Date: 2020/3/16
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        Photo photo = photoService.selectPhotoById(id);
        String lujing = photo.getLujing();

        File file = new File(lujing);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                photoService.delete(id);
                return "删除成功";

            } else {
                return "删除失败";
            }
        } else {
            return "删除失败";
        }

    }

}

