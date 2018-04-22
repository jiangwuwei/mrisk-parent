package com.zoom.risk.operating.web.controller;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.web.model.Menu;
import com.zoom.risk.operating.web.model.User;
import com.zoom.risk.operating.web.service.LoginService;
import com.zoom.risk.operating.web.service.MenuService;
import com.zoom.risk.operating.web.service.RoleService;
import com.zoom.risk.operating.web.service.UserService;
import com.zoom.risk.operating.web.utils.CookiePair;
import com.zoom.risk.operating.web.utils.SessionUtil;
import com.zoom.risk.operating.web.vo.IndexMenuVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/3/9.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    private static String USER_LOGIN_FLAG = "USER_LOGIN_FLAG";

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/verify")
    public ModelAndView login(@RequestParam(value = "loginName", required = true) String loginName,
                              @RequestParam(value = "loginPwd", required = true) String loginPwd,
                              HttpSession session,
                              HttpServletResponse response) throws UnsupportedEncodingException {
        ModelAndView view = new ModelAndView("src/login/login");
        User user = userService.selectActiveUserByName(loginName);
        if (loginService.validate(loginName, loginPwd, user)) {
            String userNameBase64 = Base64.getEncoder().encodeToString(loginName.getBytes("utf-8"));
            SessionUtil.addCookies(response, new CookiePair(SessionUtil.SESSION_ID, SessionUtil.getSessionId()), new CookiePair(SessionUtil.LOGIN_ACCOUNT, userNameBase64));
            view.addObject("topMenuList", loginService.buildTopMenu(user));
            view.setViewName("src/page/index");
            view.addObject("user", user);
        } else {
            view.addObject("errorFlag", 0);
        }
        return view;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletResponse response) {
        ModelAndView view = new ModelAndView("src/login/login");
        SessionUtil.invalidateCookies(response, SessionUtil.LOGIN_ACCOUNT, SessionUtil.SESSION_ID);
        return view;
    }

    @RequestMapping("/toMainFrame")
    public ModelAndView toMainFrame(@RequestParam(name = "menuId") long menuId,
                                    @RequestParam(name = "userId") Long userId){
        ModelAndView view = MvUtils.getView("/main");
        view.addObject("menuId", menuId);
        view.addObject("userId", userId);
        return view;
    }

    @RequestMapping("/sideBar")
    public ModelAndView sideBar(@RequestParam(name = "menuId") long menuId, @RequestParam(name = "userId") Long userId) {
        ModelAndView modelAndView = MvUtils.getView("/sidebar");
        ResultCode res = ResultCode.SUCCESS;
        List<IndexMenuVo> sideBarList = Lists.newArrayList();
        try {
            User user = userService.selectActiveUserById(userId);
            if(user != null){
                List<Menu> menus = menuService.selectAll();
                Map<Long, IndexMenuVo> menuVoMap = loginService.getMenuMap(menus);
                if (!menuVoMap.isEmpty()) {
                    if(loginService.isAdministrator(user.getLoginName())){
                        menus.forEach((menu) -> {
                            if(menu.getParentId() != null){
                                loginService.buildSideBar(menuId, menu, sideBarList, menuVoMap);
                            }
                        });
                    }else{
                        List<Long> menuIdList = roleService.selectMenuIdByRoleId(user.getRoleId());
                        menus.forEach((menu) -> {
                            if (menu.getParentId() != null && menuIdList.indexOf(menu.getId()) >= 0) {
                                loginService.buildSideBar(menuId, menu, sideBarList, menuVoMap);
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            res = ResultCode.FAILED;
        }
        modelAndView.addObject("sideBarList", sideBarList);
        modelAndView.addObject("res", res);
        return modelAndView;
    }

}
