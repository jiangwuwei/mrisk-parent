package com.zoom.risk.operating.web.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.web.dao.MenuMapper;
import com.zoom.risk.operating.web.model.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("menuService")
@Transactional()
public class MenuService {
	@Autowired
	private MenuMapper menuMapper;
	
	private static final Logger logger  = LogManager.getLogger(MenuService.class);
	
	public List<Menu> selectAll(){
		try {
			return menuMapper.selectAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	public boolean insert(Menu menu){
		try {
			menuMapper.insert(menu);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	public ResultCode delById(long menuId){
		try {
			menuMapper.deleteByPrimaryKey(menuId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR;
		}
		return ResultCode.SUCCESS;
	}

	public boolean update(Menu menu){
		try {
			menuMapper.updateByPrimaryKeySelective(menu);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	public List<Menu> selectByParentId(Long menuId){
		try {
			return menuMapper.selectByParentId(menuId);
		} catch (Exception e) {
			logger.error(e);
		}
		return Collections.EMPTY_LIST;
	}
}
