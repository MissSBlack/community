package com.ustc.community.mapper;

import com.ustc.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,tag,creator) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{tag},#{creator})")
    void create(Question question);
    @Select("select * from question limit #{offset},#{size}")
    List<Question> selAll(Integer offset,  Integer size);
    //List<Question> selAll(@Param("offset") Integer offset, @Param("size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("select count(1) from question where creator=#{personId}")
    Integer personQuestionsCount(int personId);
    @Select("select * from question where creator=#{personId} limit #{offset},#{size}")
    List<Question> showPersonQuestions(int personId, Integer offset,  Integer size);
    @Select("select * from question where id=#{id}")
    Question getQuesionById(@Param("id")Integer id);
}
