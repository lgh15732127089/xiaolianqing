package com.duoxin.dao;

import com.duoxin.pojo.Foreground;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ForegroundMapper {

    //插入
    @Insert({"insert into foreground (name,lujing,url) values (#{name},#{lujing},#{url})"})
    int insert(@Param("name")String name, @Param("lujing")String lujing, @Param("url")String url);

    //根据id查询
    @Select("select * from foreground where id = #{id}")
    Foreground selectForegroundById(@Param("id")Integer id);

    //查询
    @Select("select * from foreground")
    List<Foreground> selectForeground();

    //删除
    @Delete("delete from foreground where id = #{id}")
    void deleteForeground(@Param("id")Integer id);
}
