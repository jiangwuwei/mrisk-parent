package com.zoom.risk.operating.web.dao;

import com.zoom.risk.operating.web.model.User;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@ZoomiBatisRepository(value="userMapper")
public interface UserMapper {
    Integer deleteByPrimaryKey(Long id);

    Integer insert(User user);

    Integer insertSelective(User user);

    User selectByPrimaryKey(Long id);

    Integer selectByLoginName(String loginName);

    List<User> selectAll();

    Integer updateByPrimaryKeySelective(User record);

    Boolean updateByPrimaryKey(@Param("loginPsw") String loginPsw, @Param("id") Long id);

    User selectActiveUserByName(@Param("loginName") String loginName);

    User selectActiveUserById(@Param("userId") Long userId);
}