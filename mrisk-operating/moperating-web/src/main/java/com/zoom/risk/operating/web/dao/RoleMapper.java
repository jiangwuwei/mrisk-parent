package com.zoom.risk.operating.web.dao;

import com.zoom.risk.operating.web.model.Role;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@ZoomiBatisRepository(value="roleMapper")
public interface RoleMapper {
    List<Role> selectAll();

    Integer deleteByPrimaryKey(Long roleId);

    Integer deleteByRoleId(Long roleId);

    Integer insert(Role record);

    List<Long> selectMenuIdByRoleId(Long id);

    Long getRoleRightByRoleId(Long roleId);

    Integer insertRoleRight(@Param("roleId") Long roleId,@Param("list") List<Long> list);

    Integer selectByRoleName(String roleName);

    Integer insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(Role record);

    Integer updateByPrimaryKey(Role record);
}