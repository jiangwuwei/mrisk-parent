package com.zoom.risk.operating.web.dao;

import com.zoom.risk.operating.web.model.Menu;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@ZoomiBatisRepository(value="menuMapper")
public interface MenuMapper {
    List<Menu> selectAll();

    Integer deleteByPrimaryKey(Long id);

    Integer insert(Menu record);

    Integer insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(Menu record);

    Integer updateByPrimaryKey(Menu record);

    List<Menu> selectByParentId(@Param("menuId") Long menuId);
}