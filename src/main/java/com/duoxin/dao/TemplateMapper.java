package com.duoxin.dao;

import com.duoxin.pojo.Template;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface TemplateMapper {

    //添加
    @Insert({"insert into template (name,lujing,url,ischoose) values (#{name},#{lujing},#{url},'2')"})
    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "id")
    //@SelectKey(statement = "",before = true,keyColumn = "Template",resultType = int.class,keyProperty = "id")
    void insert(Template temp);

    //查询
    @Select("select * from template")
    List<Template> selectTemplate();

    //根据id查询
    @Select("select * from template where id = #{id}")
    Template selectTemplateById(Integer id);

    //删除
    @Delete("delete from template where id = #{id}")
    void deleteTemplate(Integer id);

    //根据id修改
    @Update("update template set ischoose = '1' where id = #{id}")
    void updateById(Integer id);

    //根据状态查询图片
    @Select("select * from template where ischoose = '1'")
    List<Template> selectTemplateByIschoose();

    //修改状态为未选中
    @Update("update template set ischoose = '2' where id = #{id}")
    void updateStateById(Integer id);

}
