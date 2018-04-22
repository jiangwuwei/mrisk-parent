package com.zoom.risk.operating.decision.controller;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.common.Constants;
import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.operating.decision.proxy.DtPolicyServiceProxy;
import com.zoom.risk.operating.decision.proxy.RiskDecisionTreeProxyService;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/dtPolicy")
public class DtPolicyController {
	
	private static final Logger logger = LogManager.getLogger(DtPolicyController.class);

	@Autowired
	private DtPolicyServiceProxy dtPolicyServiceProxy;

	@Resource(name="riskDecisionTreeProxyService")
	private RiskDecisionTreeProxyService riskDecisionTreeProxyService;

    /**
     * 跳转到编辑页面
     * @param sceneNo 场景号
     * @param policyId 默认为0，表示新增
     * @return
     */
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(@RequestParam(required=true) String sceneNo,
    		@RequestParam(defaultValue="0",required=false) long policyId,
    	@RequestParam(defaultValue="0",required=false) int editTab){
    	ModelAndView mView =MvUtils.getView("/decisiontree/DtPolicyCenter");
    	ResultCode resultCode = ResultCode.SUCCESS;
		LsManager.getInstance().check();
    	if(policyId == 0){//新增
    		TPolicy policy = new TPolicy();
    		policy.setSceneNo(sceneNo);
    		policy.setStatus(Constants.STATUS_DRAFT);
    		mView.addObject("policy",policy);
    	}else{//更新策略
    		TPolicy policy = dtPolicyServiceProxy.selectById(policyId);
			mView.addObject("policy",policy);
    	}

    	mView.addObject("res", resultCode);
    	mView.addObject("editTab", editTab);
    	return mView;
    }
    
    @RequestMapping("/insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(TPolicy policy){
    	try {
    		if(dtPolicyServiceProxy.exists(policy.getSceneNo(), policy.getWeightValue(), policy.getId(), policy.getName())){
    			return MvUtils.formatJsonResult(new ResultCode(-1, "该场景"+policy.getSceneNo()+"下已经存在相同执行顺序的策略，或有相同名称的策略"));
    		}
    		if(policy.getId() == null){
				return MvUtils.formatJsonResult(dtPolicyServiceProxy.insert(policy), "policyId:"+policy.getId(), "sceneNo:"+policy.getSceneNo());
    		}
    	} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(dtPolicyServiceProxy.update(policy), "policyId:"+policy.getId(), "sceneNo:"+policy.getSceneNo());
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestParam long id,
    		@RequestParam int toStatus){
    	if(id<=0 || toStatus < 1){
    		return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
    	}
 
    	try {
    		if ( toStatus == 2 ){   //生效时 要验证
				riskDecisionTreeProxyService.mockDecisionTree2Rule(id);
			}
    		dtPolicyServiceProxy.updateStatus(id, toStatus);
			return MvUtils.formatJsonResult(ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
		} 
    	return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }
    
    @RequestMapping("/delById")
    public Map<String, Object> delById(@RequestParam(value="id",required=true) long id){
    	if(id<=0){
    		return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
    	}
    	
    	return MvUtils.formatJsonResult(dtPolicyServiceProxy.delById(id));
    }
}
