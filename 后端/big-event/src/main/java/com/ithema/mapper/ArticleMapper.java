package com.ithema.mapper;

import com.ithema.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //新增
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    void add(Article article);


    List<Article> list(Integer userId, Integer categoryId, String state);

    @Delete("delete from article where id=#{id}")
    void deleteById(Integer id);
    @Update("update article set title=#{title},cover_img=#{coverImg},state =#{state},content = #{content},category_id = #{categoryId},update_time=now() where id=#{id}")
    void update(Article article);
}
