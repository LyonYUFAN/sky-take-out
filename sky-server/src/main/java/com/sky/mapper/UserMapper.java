package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 通过openid查找用户信息
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getUserByOpenid(String openid);

    /**
     * 插入用户信息
     * @param user
     */
    void insert(User user);

    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * 通过Map查询用户数量
     * @param map
     * @return
     */
    Integer sumByMap(Map map);
}
