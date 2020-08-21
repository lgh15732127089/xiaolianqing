package com.duoxin.controller;


import com.duoxin.pojo.Template;
import com.duoxin.service.PhotoService;
import com.duoxin.service.TemplateService;
import com.duoxin.utils.BaseUtils;
import com.duoxin.utils.ForegroundUtils;
import com.duoxin.utils.RenameUtils;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.imageio.ImageIO.read;
import static org.springframework.web.util.WebUtils.getCookie;

//模板
@Controller
public class TemplateController {

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private PhotoService photoService;

    //前景图要存储的路径
    @Value("${foregroundpath}")
    private String foregroundpath;

    //前景图要存储的url
    @Value("${foregroundurl}")
    private String foregroundurl;

    //图片要存储的路径
    @Value("${photopath}")
    private String photopath;

    //图片要存储的url
    @Value("${photourl}")
    private String photourl;

    //模板要存储的路径
    @Value("${templatepath}")
    private String templatepath;

    //模板要存储的url
    @Value("${templateurl}")
    private String templateurl;

    //图片的base值
    private String imgString;


    /**
     * @Description: 传入两个图片，返回合并图片的url  一个为前景图片，一个为后景图片
     * @Param: string
     * @return: string
     * @Author: lgh
     * @Date: 2020/3/16
     */

    @RequestMapping(value = "/uploadTemplate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadTemplate(@RequestParam("fileOne") MultipartFile fileOne, @RequestParam("fileTwo") MultipartFile fileTwo) throws IOException {


        return null;

    }


    /**
     * @Description: 查询所有模板，返回模板的路径
     * @Param: null
     * @return: list
     * @Author: lgh
     * @Date: 2020/3/18
     */

    @RequestMapping(value = "/selectTemplate")
    @ResponseBody
    public List<Template> selectTemplate() {

        List<Template> templateList = templateService.selectTemplate();


        return templateList;
    }


    /**
     * @Description: 根据id查询选中的图片
     * @Param: list
     * @return: list
     * @Author: lgh
     * @Date: 2020/3/19
     */
    @PostMapping(value = "/selectTemplateById")
    public void selectTemplateById(@RequestBody List<Integer> ids) {

        //选中的图片
        for (Integer id : ids) {
            //查询选中的图片
            Template template = templateService.selectTemplateById(id);
            //如果被选中就修改数据的状态
            templateService.updateById(id);
        }

    }


    /**
     * @Description: 根据前端传入的id，删除后台的模板照片
     * @Param: Integer
     * @return: string
     * @Author: lgh
     * @Date: 2020/3/17
     */

    @RequestMapping("/deleteTemplate/{id}")
    @ResponseBody
    public String deleteTemplate(@PathVariable("id") Integer id) {

        Template template = templateService.selectTemplateById(id);

        String lujing = template.getLujing();

        File file = new File(lujing);


        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                templateService.deleteTemplate(id);
                return "删除成功";
            } else {
                return "删除失败";
            }
        } else {
            return "删除失败";
        }

    }


    /**
     * @Description: 调用此接口生成二维码   http://192.168.56.1:8080/QrCode?imageName=？
     * @Param: string
     * @return: string
     * @Author: lgh
     * @Date: 2020/3/17
     */

    @RequestMapping("/QrCode")
    @ResponseBody
    public String QrCode() {
        //二维码大小
        final int width = 300;
        final int height = 300;
        //格式
        final String format = "jpg";
        //二维码包含的内容，文本或网址
        final String content = "http://192.168.1.56:8081/downloadTemplate";
        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        //生成二维码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            //生成二维码存放的位置
            Path file = new File("G:\\Java\\IdeaProjects\\xiaolianqiang\\src\\main\\resources\\static\\images\\img.png").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            return "http://192.168.1.56:8081/images/img.png";
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @Description: 解析二维码
     * @Param: request
     * @return: map
     * @Author: lgh
     * @Date: 2020/3/17
     */

    @RequestMapping("/parse")
    @ResponseBody
    public Map test04(HttpServletRequest req) throws Exception {
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File("G:\\Java\\IdeaProjects\\xiaolianqiang\\src\\main\\resources\\static\\images\\img.png");

        BufferedImage image = ImageIO.read(file);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        Result result = formatReader.decode(binaryBitmap, hints);


        Map map = new HashMap();
        map.put("text", result.getText());
        return map;
    }


    /**
     * @Description: 模板下载，传入要下载的 模板名称     模板下载路径 http://localhost:8080/downloadForeground
     * @Param: string
     * @return: list
     * @Author: lgh
     * @Date: 2020/3/18
     */

    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
    @ResponseBody
    public List downloadTemplate() {

        //查询数据库中要下载的照片
        List<Template> templateList = templateService.selectTemplateByIschoose();

        if (templateList == null && templateList.size() == 0) {
            return null;
        }

        for (Template template : templateList) {
            //修改状态为未选中

            templateService.updateStateById(template.getId());
        }

        return templateList;
    }

    /**
     * @Description: 传入一个图片批量生成模板
     * @Param: photo
     * @return: string
     * @Author: lgh
     * @Date: 2020/3/18
     */

    @RequestMapping(value = "/batchTemplate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchTemplate(@RequestParam("photo") MultipartFile photo) throws IOException {



        //返回是否成功
        return "添加成功";

    }
    //base64字符串转化成图片
    @RequestMapping("/imgStrBatch")
    @ResponseBody
    public List<Template> GenerateImage() throws IOException {   //对字节数组字符串进行Base64解码并生成图片

        if (imgString == null) { //图像数据为空
            return null;
        }
        //这个是图片的根路径加图片名称
        String parse = BaseUtils.parse(imgString, photopath);
        //这个是图片的名称
        String name = RenameUtils.rePath(parse);
        //把图片存储到数据库中
        int  i= photoService.insertUrl(name, parse, photourl + name);
        if (i == 0){
            return null;
        }

        //要返回的值
        List<Template> templateList = new ArrayList<Template>();

        if (parse != "上传失败" && parse != null){
            //进行合并

            //上级文件夹
            File parentFile = new File(foregroundpath);

            //判断是否是目录 不是就创建一个
            if (!parentFile.isDirectory()) {
                parentFile.mkdir();
            }
            File[] fileList = parentFile.listFiles();

            //这个是图片
            //BufferedImage img = read(new File(path + fileName));//img

            //遍历文件夹下的所有文件
            for (File files : fileList) {
                //这个是图片
                BufferedImage img = read(new File(parse));//img
                //这个是前景图
                BufferedImage maskSnow = read(files);

                //使用工具类生成新的图片
                //BufferedImage img2 = NewImageUtils.generateImg(img, maskSnow);
                //使用图片命名工具类
                String templateName = RenameUtils.rename(".jpg");
                //要返回的路径
                String result = templatepath + templateName;
                //要返回的url
                String templateUrl = templateurl + templateName;

                //写入到路径上
                ImageIO.write(img, "jpeg", new File(result));

                //存到数据库中
                Template template = templateService.insert(templateName, result, templateUrl);

                templateList.add(template);
            }
            return templateList;
        }
        return null;
    }

    //base64字符串存储
    @PostMapping("/imgStr")
    @ResponseBody
    public String image(@RequestBody String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        //原来是通过这个接口接收的现在通过websocket接收

        imgString = imgStr;
        if (imgString.isEmpty()){
            return "下载失败";
        }
        return "下载成功";
    }

}



