package com.duoxin.service;

import com.duoxin.dao.PhotoMapper;
import com.duoxin.pojo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Component
@Service
public class PhotoService {
    @Autowired
    private PhotoMapper photoMapper;

    //插入
    public int insertUrl(String name,String lujing,String url){
        int jieguo=photoMapper.insertUrl(name,lujing,url);
        return jieguo;
    }
    //查询
    public List<Photo> select(){
        List<Photo> photos=photoMapper.select();
        return  photos;
    }

    //根据id删除图片
    public void delete(Integer id) {
        photoMapper.delete(id);
    }

    //根据id查询图片
    public Photo selectPhotoById(Integer id) {
        Photo photo = photoMapper.selectPhotoById(id);
        return photo;
    }

    //定时删除数据库所有照片
    public void deleteAll() {
        photoMapper.deleteAll();
    }

}
