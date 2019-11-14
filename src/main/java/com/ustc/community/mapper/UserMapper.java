package com.ustc.community.mapper;

import com.ustc.community.model.User;
import org.apache.ibatis.annotations.*;

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
    @Select("select * from user where account_id=#{accountId}")
    User getUserByAccountId(@Param("accountId") String accountId);
    @Update("update user set name=#{name},avator_url=#{avatorUrl},gmt_modified=#{gmtModified} where account_id=#{accountId}")
    void updateUser(@Param("name") String name,@Param("avatorUrl") String avatorUrl,@Param("gmtModified") Long gmtModified,@Param("accountId") String accountId);
}
