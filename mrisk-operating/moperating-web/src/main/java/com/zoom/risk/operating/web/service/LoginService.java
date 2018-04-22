package com.zoom.risk.operating.web.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zoom.risk.operating.web.model.Menu;
import com.zoom.risk.operating.web.model.User;
import com.zoom.risk.operating.web.vo.IndexMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/6/13.
 */
@Service("loginService")
@Transactional()
public class LoginService {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * build index top menu for user
     * @param user
     * @return
     */
    public List<IndexMenuVo> buildTopMenu(User user) {
        List<IndexMenuVo> topList = Lists.newArrayList();
        List<Menu> menus = menuService.selectAll();
        Map<Long, IndexMenuVo> menuMap = this.getMenuMap(menus);
        if (!menuMap.isEmpty()) {
            if(isAdministrator(user.getLoginName())){
                menus.forEach(menu -> {
                    if(menu.getParentId() == null){
                        topList.add(menuMap.get(menu.getId()));
                    }
                });
            }else{
                if ( user.getRoleId() != null ) {
                    List<Long> menuIdList = roleService.selectMenuIdByRoleId(user.getRoleId());
                    menus.forEach((menu) -> {
                        if (menuIdList.indexOf(menu.getId()) >= 0 && menu.getParentId() == null) {
                            topList.add(menuMap.get(menu.getId()));
                        }
                    });
                }
            }
        }
        return topList;
    }

    /**
     * check if the login user is administrator
     * @param loginName
     * @return
     */
    public boolean isAdministrator(String loginName){
        if("Administrator".equals(loginName)){
            return true;
        }
        return false;
    }

    /**
     * transfer menu list to a map whose key is menu's id and value is the responding object menu
     * @param menus
     * @return
     */
    public Map<Long, IndexMenuVo> getMenuMap(List<Menu> menus) {
        Map<Long, IndexMenuVo> menuMap = Maps.newHashMap();
        if (menus != null && !menus.isEmpty()) {
            menus.forEach((menu) -> {
                menuMap.put(menu.getId(), new IndexMenuVo(menu.getId(), menu.getMenuName(), menu.getIconcls(), menu.getMenuUrl()));
            });
        }
        return menuMap;
    }

    /**
     * user authentication
     * @param loginName
     * @param loginPwd
     * @param user
     * @return
     */
    public boolean validate(String loginName, String loginPwd, User user) {
        if (user != null && user.getLoginName().equals(loginName)
                && user.getLoginPsw().equals(loginPwd)) {
            return true;
        }
        return false;
    }

    /**
     * build side bar menu for user
     * @param menuId
     * @param menu
     * @param sideBarList
     * @param menuVoMap
     */
    public void buildSideBar(Long menuId, Menu menu, List<IndexMenuVo> sideBarList,  Map<Long, IndexMenuVo> menuVoMap){
        //parent menu
        if (menuId == menu.getParentId() || menu.getId() == menuId) {
            sideBarList.add(menuVoMap.get(menu.getId()));
        } else {
            IndexMenuVo parentVo = menuVoMap.get(menu.getParentId());
            parentVo.addChild(menuVoMap.get(menu.getId()));
        }
    }
}
