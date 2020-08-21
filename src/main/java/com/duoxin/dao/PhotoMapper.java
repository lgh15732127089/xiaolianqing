package com.duoxin.dao;

import com.duoxin.pojo.Photo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PhotoMapper {

    //插入
    @Insert({"insert into photo (name,lujing,url) values (#{name},#{lujing},#{url})"})
    public int insertUrl(@Param("name")String name,@Param("lujing")String lujing,@Param("url")String url);

    //查询
    @Select("select * from photo")
    public List<Photo> select();

    //删除
    @Delete("delete from photo where id = #{id}")
    public void delete(@Param("id") Integer id);

    //根据id查询
    @Select("select * from photo where id = #{id}")
    public Photo selectPhotoById(@Param("id")Integer id);

    //删除所有
    @Delete("delete from photo")
    public void deleteAll();
}
