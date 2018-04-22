package com.zoom.risk.operating.web.controller;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.operating.web.model.Menu;
import com.zoom.risk.operating.web.model.Role;
import com.zoom.risk.operating.web.model.User;
import com.zoom.risk.operating.web.service.MenuService;
import com.zoom.risk.operating.web.service.RoleService;
import com.zoom.risk.operating.web.service.UserService;
import com.zoom.risk.operating.web.vo.MenuVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
@RequestMapping("/system")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
    /**
     * 跳转到用户界面
     * @return
     */
    @RequestMapping("/user")
    public ModelAndView getUsers(){
    	ModelAndView mView =MvUtils.getView("/system/user");
		List<User> users = userService.selectAll();
		List<Role> roles = roleService.selectAll();
		mView.addObject("roles",roles);
    	mView.addObject("users", users);
    	return mView;
    }
	@RequestMapping("/insertUser")
	@ResponseBody
	public Map<String, Object> insertUser(User user){
		try {
			if(userService.existUser(user.getLoginName())){
				return MvUtils.formatJsonResult(new ResultCode(-1, "此用户名已存在"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		user.setStatus(1);
		//user.setLoginPsw(Md5Utils.encrypt16(user.getLoginPsw()));
		return MvUtils.formatJsonResult(userService.insert(user));
	}
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, Object> updateUser(User user){
		return MvUtils.formatJsonResult(userService.update(user));
	}
	@RequestMapping("/delUserById")
	@ResponseBody
	public Map<String, Object> delUserById(@RequestParam(value="id",required=true) long id){
		if(id<=0){
			return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
		}
		return MvUtils.formatJsonResult(userService.delById(id));
	}
    @RequestMapping("/role")
	public ModelAndView getRole(){
		ModelAndView mView = MvUtils.getView("/system/role");
		List<Role> roles = roleService.selectAll();
		mView.addObject("roles",roles);
		return mView;
	}
	@RequestMapping("/insertRole")
	@ResponseBody
	public Map<String, Object> insertRole(Role role){
		try {
			if(roleService.existRole(role.getRoleName())){
				return MvUtils.formatJsonResult(new ResultCode(-1, "此角色已存在"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(roleService.insert(role));
	}
	@RequestMapping("/updateRole")
	@ResponseBody
	public Map<String, Object> updateRole(Role role){
		return MvUtils.formatJsonResult(roleService.update(role));
	}
	@RequestMapping("/delRoleById")
	@ResponseBody
	public Map<String, Object> delRoleById(@RequestParam(value="id",required=true) long id){
		if(id<=0){
			return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
		}
		return MvUtils.formatJsonResult(roleService.delById(id));
	}
    @RequestMapping("/menu")
	public ModelAndView getMenu() {
		ModelAndView mView = MvUtils.getView("/system/menu");
		List<Menu> menus = menuService.selectAll();
		Map<Long, Menu> idMap = new HashMap<>();
		menus.forEach( menu -> {
			idMap.put(menu.getId(), menu);
		});
		Iterator<Menu> it = menus.iterator();
		while ( it.hasNext() ){
			Menu menu = it.next();
			if ( menu.getParentId() != null && (!"".equals(menu.getParentId()+"")) ){
				idMap.get(menu.getParentId()).addChild(menu);
				it.remove();
			}
		}
		mView.addObject("menus", menus);
		return mView;
	}


	@ResponseBody
	@RequestMapping("/findMenuByRole")
	public Map<String,List<MenuVo>>  findMenuByRole(@RequestParam(value="roleId",required=true) Long roleId){
		List<Menu> menus = menuService.selectAll();
        List<Long> checkedMenulist = roleService.selectMenuIdByRoleId(roleId);
		List<MenuVo> menuVoList = new ArrayList<>();
		Map<Long,MenuVo> allMap = new HashMap<>();
		Map<Long,MenuVo> allpMap = new HashMap<>();
		menus.forEach(
			menu -> {
				MenuVo vo = menu.copyOne();
				vo.setIconSkin(null);
				menuVoList.add(vo);
				allMap.put(vo.getId(),vo);
				if ( vo.getpId() != null ) {
					allpMap.put(vo.getpId(), vo);
				}
			}
		);
		menuVoList.forEach( vo->{
			if ( vo.getpId() == null ) {
				vo.setIconSkin("pIcon01");
			}else{
				if ( allpMap.get(vo.getId()) == null ){
					vo.setIconSkin("icon06");
				}
			}
		});
        menuVoList.forEach( vo->{
            checkedMenulist.forEach(menuId->{
                if ( vo.getId().equals(menuId)) {
                        vo.setChecked(true);
                }
            });
        });
		Map<String,List<MenuVo>> resultMap = new HashMap<>();
		resultMap.put("nodes", menuVoList);
		logger.info("JSON : {}", JSON.toJSONString(menuVoList));
		return resultMap;
	}
    @RequestMapping("/saveRoleRight")
    @ResponseBody
    public Map<String, Object> saveRoleRight(@RequestParam(value="roleId",required=true)Long roleId,
                                             @RequestParam(value="menuIds",required=false)Long[] menuIds){
        try {
            return MvUtils.formatJsonResult(roleService.insertRoleRight(roleId,menuIds));
        } catch (Exception e) {
            logger.error(e);
            return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
        }
    }

	@RequestMapping("/insertMenu")
	@ResponseBody
	public Map<String, Object> insertMenu(Menu menu){
		try {
			return MvUtils.formatJsonResult(menuService.insert(menu));
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
	}

	@RequestMapping("/updateMenu")
	@ResponseBody
	public Map<String, Object> updateMenu(Menu menu){
		return MvUtils.formatJsonResult(menuService.update(menu));
	}
	@RequestMapping("/updatePsw")
	@ResponseBody
	public Map<String, Object> updateByPsw(@RequestParam(value = "userId",required = true) Long userId,@RequestParam(value = "psw",required = true) String psw,@RequestParam(value = "prePass",required = true) String prePass){
		Map<String,Object> map = new HashMap<>();
		if (userService.selectActiveUserById(userId).getLoginPsw().equals(prePass)){
			return MvUtils.formatJsonResult(userService.updateByPsw(psw,userId));
		}else {
			map.put("errMsg","输入的原密码不正确！");
			return map;
		}
	}
	@RequestMapping("/delMenuById")
	@ResponseBody
	public Map<String, Object> delMenuById(@RequestParam(value="id",required=true) long id){
		if(id<=0){
			return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
		}
		return MvUtils.formatJsonResult(menuService.delById(id));
	}
}
