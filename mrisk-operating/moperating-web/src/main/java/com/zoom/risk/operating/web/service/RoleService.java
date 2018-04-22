package com.zoom.risk.operating.web.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.web.dao.RoleMapper;
import com.zoom.risk.operating.web.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("roleService")
@Transactional()
public class RoleService {
	@Autowired
	private RoleMapper roleMapper;
	
	private static final Logger logger  = LogManager.getLogger(RoleService.class);
	
	public List<Role> selectAll(){
		try {
			return roleMapper.selectAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	public boolean existRole(String name){
		return (roleMapper.selectByRoleName(name))>0?true:false;
	}
	public boolean insert(Role role){
		try {
			roleMapper.insert(role);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	public boolean insertRoleRight(Long roleId,Long[] menuIds){
		ArrayList<Long> arrayList = new ArrayList<>();
		try {
			if (menuIds==null){
				roleMapper.deleteByRoleId(roleId);
			}
			else {
				for (int i = 0; i < menuIds.length; i++) {
					arrayList.add(menuIds[i]);
				}
				roleMapper.deleteByRoleId(roleId);
				roleMapper.insertRoleRight(roleId,arrayList);
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	public ResultCode delById(long roleId){
		try {
			roleMapper.deleteByPrimaryKey(roleId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR;
		}
		return ResultCode.SUCCESS;
	}
	public boolean update(Role role){
		try {
			roleMapper.updateByPrimaryKeySelective(role);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	public List<Long> selectMenuIdByRoleId(Long id){
		try {
			return roleMapper.selectMenuIdByRoleId(id);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}

}
