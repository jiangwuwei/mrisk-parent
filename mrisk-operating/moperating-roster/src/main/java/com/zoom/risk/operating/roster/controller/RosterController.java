package com.zoom.risk.operating.roster.controller;

import com.zoom.risk.operating.roster.proxy.RosterProxyService;
import com.zoom.risk.operating.roster.vo.Page;
import com.zoom.risk.operating.roster.vo.QueryResult;
import com.zoom.risk.operating.roster.vo.Roster;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller("rosterController")
@RequestMapping("/rosterController")
public class RosterController {
	private static final Logger logger = LogManager.getLogger(RosterController.class);
	private static final String LE_LOGIN_ACCOUNT = "LE_LOGIN_ACCOUNT";


	@Resource(name="rosterProxyService")
	private RosterProxyService rosterProxyService;

	@ResponseBody
	@RequestMapping("/rosterBatchSave")
	public Map<String,Object> rosterBatchSave(@RequestParam(value="rosterBusiType", required=true) Integer rosterBusiType,
										  @RequestParam(value="rosterOrigin", required=true) Integer rosterOrigin,
										  @RequestParam(value="rosterType", required=true) Integer rosterType,
										  @RequestParam(value="content", required=true) String content,
										  @CookieValue(value=LE_LOGIN_ACCOUNT) String loginName  ) {
		Map<String,Object> result = new HashMap<>();
		boolean resultCode = true;
		String[] ct = content.replaceAll("\r","").split("\n");
		List<String> contents = new ArrayList<>();
		for (String s : ct) {
			if ( s != null && s.trim().length() > 0 ) {
				contents.add(s.trim());
			}
		}
		List<String> errorList = Collections.emptyList();
		String operator = new String(Base64.getDecoder().decode(loginName));
		try{
			errorList = rosterProxyService.rosterBatchSave(rosterBusiType, rosterOrigin, rosterType, operator, contents);
		}catch (Exception e){
			resultCode = false;
			logger.error("", e);
		}
		result.put("resultCode", resultCode);
		result.put("errorList", errorList);
		return result;
	}

	@RequestMapping("/rosterBusiType")
    public ModelAndView rosterBusiType(){
        ModelAndView view = new ModelAndView("/src/page/roster/RosterConfig");
        List<Map<String,Object>> sceneList = rosterProxyService.getAllScene();
        List<Map<String,Object>> rosterTypeList = rosterProxyService.getTypeByCode("RosterBusiType");
        view.addObject("sceneList",sceneList);
        view.addObject("rosterTypeList",rosterTypeList);
        return view;
    }

	@RequestMapping("/rosterType")
    public ModelAndView rosterType(){
		ModelAndView view = new ModelAndView("/roster/RosterSave");
		List<Map<String,Object>> rosterBusiTypeTypeList = rosterProxyService.getTypeByCode("RosterBusiType");
		List<Map<String,Object>> rosterTypeList = rosterProxyService.getTypeByCode("RosterType");
		view.addObject("rosterBusiTypeList",rosterBusiTypeTypeList);
		view.addObject("rosterTypeList",rosterTypeList);
		return view;
	}

	@ResponseBody
    @RequestMapping("/getSceneNo")
    public List<Map<String,Object>> getSceneNo(@RequestParam(value = "rosterBusiType", required = true) int rosterBusiType){
        return rosterProxyService.getSceneNo(rosterBusiType);
    }

	@ResponseBody
    @RequestMapping("/rosterRestrictionSave")
    public Map<String,Object> rosterRestrictionSave(@RequestParam(value = "rosterBusiType", required = true) int rosterBusiType,@RequestParam(value = "scenes", required = false) String[] scenes){
        Map<String,Object> map = new HashMap<>();
		List<String> sceneList = new ArrayList<>();
        boolean flag = true;
		if (scenes!=null){
        	sceneList = Arrays.asList(scenes);
		}else {
			sceneList = null;
		}
        try{
			rosterProxyService.batchInsertMap(sceneList,rosterBusiType);
        }catch(Exception e){
            flag = false;
            logger.error(e);
        }
        map.put("resultCode",flag);
        return map;
    }

	@ResponseBody
	@RequestMapping("/getRosterOrigin")
    public List<Map<String,Object>> getRosterOrigin(@RequestParam(value = "rosterBusiType", required = true) int rosterBusiType){
		return rosterProxyService.getRosterOrigin(rosterBusiType);
	}

	@ResponseBody
	@RequestMapping("/getContentList")
	public ModelAndView contentList(@RequestParam(value = "rosterBusiType", required = false) Integer rosterBusiType,
								   	@RequestParam(value="rosterOrigin", required=false) Integer rosterOrigin,
								   	@RequestParam(value="rosterType", required=false) Integer rosterType,
								   	@RequestParam(value="content", required=false) String content,
								   	@RequestParam(value="currentPage", required=false) Integer currentPage,
									@RequestParam(value="pageSize",required=false) Integer pageSize
	){
		ModelAndView view = new ModelAndView("/src/page/roster/RosterList");
		LsManager.getInstance().check();
		if(rosterBusiType == null){
			rosterBusiType = 100;
		}
		if ( currentPage == null ){
			currentPage = 1;
		}
		if ( pageSize == null ){
			pageSize = 15;
		}
        QueryResult<Roster> queryResult = rosterProxyService.getContentList(rosterBusiType,rosterOrigin,rosterType,content,currentPage,pageSize);
		List<Roster> list = queryResult.getList();
		Page page = new Page(queryResult.getTotalCount(), currentPage);
		page.setPageSize(pageSize);
		List<Map<String,Object>> rosterBusiTypeTypeList = rosterProxyService.getTypeByCode("RosterBusiType");
		List<Map<String,Object>> rosterTypeList = rosterProxyService.getTypeByCode("RosterType");
		view.addObject("rosterBusiTypeList",rosterBusiTypeTypeList);
		view.addObject("rosterTypeList",rosterTypeList);
		view.addObject("list",list);
		view.addObject("page",page);
		view.addObject("rosterBusiType",rosterBusiType);
		view.addObject("rosterType",rosterType);
		return view;
	}

	@ResponseBody
	@RequestMapping("/deleteRoster")
	public Map<String,Object> deleteRoster(@RequestParam(value = "rosterBusiType", required = false) Integer rosterBusiType,
							 @RequestParam(value = "id", required = true) List<Long> ids){
		Map<String,Object> result = new HashMap<>();
		boolean resultCode = true;
		String operator = "Admin";
		try{
			rosterProxyService.deleteRoster(rosterBusiType,operator,ids);
		}catch (Exception e){
			resultCode = false;
			logger.error("", e);
		}
		result.put("resultCode", resultCode);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getLogList")
	public ModelAndView getLogList(@RequestParam(value = "rosterBusiType", required = false) Integer rosterBusiType,
								   @RequestParam(value = "rosterType", required = false) Integer rosterType,
								   @RequestParam(value="content", required=false) String content,
								   @RequestParam(value="currentPage", required=false) Integer currentPage,
								   @RequestParam(value="pageSize",required=false) Integer pageSize
	){
		ModelAndView view = new ModelAndView("/src/page/roster/RosterLogList");
		if ( currentPage == null ){
			currentPage = 1;
		}
		if ( pageSize == null ){
			pageSize = 15;
		}
		QueryResult<Map<String,Object>> queryResult = rosterProxyService.getLogList(rosterBusiType, rosterType, content,currentPage,pageSize);
		List<Map<String,Object>> list = queryResult.getList();
		Page page = new Page(queryResult.getTotalCount(), currentPage);
		page.setPageSize(pageSize);
		List<Map<String,Object>> rosterBusiTypeTypeList = rosterProxyService.getTypeByCode("RosterBusiType");
		List<Map<String,Object>> rosterTypeList = rosterProxyService.getTypeByCode("RosterType");
		view.addObject("rosterTypeList",rosterTypeList);
		view.addObject("rosterBusiTypeList",rosterBusiTypeTypeList);
		view.addObject("list",list);
		view.addObject("page",page);
		view.addObject("rosterBusiType",rosterBusiType);
		view.addObject("rosterType",rosterType);
		return view;
	}

}
