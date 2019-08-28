package com.ustc.community.mapper;

import com.ustc.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{tag})")
    void create(Question question);
}
