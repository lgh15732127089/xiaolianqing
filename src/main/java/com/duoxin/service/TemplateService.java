package com.duoxin.service;

import com.duoxin.dao.TemplateMapper;
import com.duoxin.pojo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {


    @Autowired
    private TemplateMapper templateMapper;

    //添加
    public Template insert(String result, String fileName, String url) {
        Template t = new Template();
        t.setName(result);
        t.setLujing(fileName);
        t.setUrl(url);
        templateMapper.insert(t);
        return t;

    }

    //查询模板
    public List<Template> selectTemplate() {

        List<Template> templateList = templateMapper.selectTemplate();
        return templateList;
    }

    //根据id查询模板
    public Template selectTemplateById(Integer id) {
        return templateMapper.selectTemplateById(id);
    }

    //根据删除模板
    public void deleteTemplate(Integer id) {
        templateMapper.deleteTemplate(id);
    }

    //根据id修改状态  为选中
    public void updateById(Integer id) {
        templateMapper.updateById(id);
    }

    //根据状态查询照片
    public List<Template> selectTemplateByIschoose() {
        List<Template> templateList = templateMapper.selectTemplateByIschoose();
        return templateList;
    }

    //根据id修改状态  为未选中
    public void updateStateById(Integer id) {
        templateMapper.updateStateById(id);
    }
}
