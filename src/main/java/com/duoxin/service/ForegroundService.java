package com.duoxin.service;

import com.duoxin.dao.ForegroundMapper;
import com.duoxin.pojo.Foreground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class ForegroundService {

    @Autowired
    private ForegroundMapper foregroundMapper;

    //添加
    public int insert(String fileName, String path, String url) {
        int jieguo = foregroundMapper.insert(fileName,path,url);
        return jieguo;
    }
    //根据id查询
    public Foreground selectForegroundById(Integer id) {
        Foreground foreground = foregroundMapper.selectForegroundById(id);
        return foreground;
    }

    //查询所有
    public List<Foreground> selectForeground() {
        List<Foreground> foregrounds = foregroundMapper.selectForeground();
        return foregrounds;
    }

    //根据id删除
    public void deleteForeground(Integer id) {
        foregroundMapper.deleteForeground(id);
    }
}
