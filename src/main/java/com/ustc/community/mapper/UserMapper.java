package com.ustc.community.mapper;

import com.ustc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("Insert into user (account_id,name,token,gmt_create,gmt_modified,avator_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatorUrl})")
    void insterUser(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user where account_id=#{accountId} ")
    User findByAccountId(String accountId);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
