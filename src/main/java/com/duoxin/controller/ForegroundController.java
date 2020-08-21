package com.duoxin.controller;


import com.duoxin.pojo.Foreground;
import com.duoxin.pojo.Photo;
import com.duoxin.service.ForegroundService;
import com.duoxin.utils.BaseUtils;
import com.duoxin.utils.ForegroundUtils;
import com.duoxin.utils.RenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//前景图
@Controller
public class ForegroundController {

    @Autowired
    private ForegroundService foregroundService;

    //前景图要存储的路径
    @Value("${foregroundpath}")
    private String foregroundpath;

    //前景图要存储的url
    @Value("${foregroundurl}")
    private String foregroundurl;

    /**
     * @Description: 上传前景图，返回上传是否成功
     * @Param: file
     * @return: String
     * @Author: lgh
     * @Date: 2020/3/17
     */
    @RequestMapping(value = "/uploadForeground", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadForeground(@RequestParam("fileName") MultipartFile file) {


            return foregroundurl ;



    }

    /**
     * @Description: 查询所有前景照片，返回前景照片的路径
     * @Param: null
     * @return: list
     * @Author: lgh
     * @Date: 2020/3/17
     */
    @RequestMapping(value = "/selectForeground")
    @ResponseBody
    public List<Foreground> selectForeground() {

        List<Foreground> foregrounds = foregroundService.selectForeground();


        return foregrounds;
    }

    /**
     * @Description: 根据前端传入的id，删除后台的前景照片
     * @Param: Integer
     * @return: null
     * @Author: lgh
     * @Date: 2020/3/17
     */
    @RequestMapping("/deleteForeground/{id}")
    @ResponseBody
    public String deleteForeground(@PathVariable("id") Integer id) {

        Foreground foreground = foregroundService.selectForegroundById(id);
        String lujing = foreground.getLujing();

        File file = new File(lujing);


        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                foregroundService.deleteForeground(id);
                return "删除成功";
            } else {
                return "删除失败";
            }
        } else {
            return "删除失败";
        }

    }

    //base64字符串存储
    @PostMapping("/foregroundImgStr")
    @ResponseBody
    public String image(@RequestBody String foregroundImgStr) {   //对字节数组字符串进行Base64解码并生成图片
        //返回前景图的路径和名称
        String parse = BaseUtils.parse(foregroundImgStr, foregroundpath);

        //判断是否有数据
        if (!parse.isEmpty()){
            try {
                //将前景图做透明层
                ForegroundUtils.dispose(parse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //前景图名称
        String foregroundName = RenameUtils.rePath(parse);

        //返回存储数据库结果
        int jieguo = foregroundService.insert(foregroundName, parse, foregroundurl + foregroundName);

        if (jieguo == 0) {
            return "上传失败";
        } else {
            return foregroundurl + foregroundName;
        }
    }


}
