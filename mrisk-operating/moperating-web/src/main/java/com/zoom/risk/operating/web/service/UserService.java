package com.zoom.risk.operating.web.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.web.dao.UserMapper;
import com.zoom.risk.operating.web.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional()
public class UserService {
	@Autowired
	private UserMapper userMapper;

	private static final Logger logger  = LogManager.getLogger(UserService.class);
	
	public List<User> selectAll(){
		try {
			return userMapper.selectAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	public boolean existUser(String name){
			return (userMapper.selectByLoginName(name))>0?true:false;
	}

	public boolean insert(User user){
		try {
			userMapper.insert(user);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	public ResultCode delById(long userId){
		try {
			userMapper.deleteByPrimaryKey(userId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean update(User user){
		try {
			userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	public boolean updateByPsw(String psw, Long id){
		return userMapper.updateByPrimaryKey(psw, id);
	}

	public User selectActiveUserByName(String loginName){
        try {
            return userMapper.selectActiveUserByName(loginName);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public User selectActiveUserById(Long userId){
        try {
            return userMapper.selectActiveUserById(userId);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
}
