package com.zoom.risk.operating.scard.controller;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.operating.scard.model.SCard;
import com.zoom.risk.operating.scard.model.SCardParamVo;
import com.zoom.risk.operating.scard.service.SCardParamService;
import com.zoom.risk.operating.scard.service.SCardRouterService;
import com.zoom.risk.operating.scard.service.SCardRuleService;
import com.zoom.risk.operating.scard.service.SCardService;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/scard")
public class SCardController {
    private static final Logger logger = LogManager.getLogger(SCardController.class);

    @Autowired
    private SCardService scardDefinitionService;
    @Autowired
    private SCardParamService scardParamService;
    @Autowired
    private SCardRouterService scardRouterService;
    @Autowired
    private SCardRuleService scardRuleService;

    /**
     * 跳转到编辑页面
     *
     * @param sceneNo  场景号
     * @param scardId 默认为0，表示新增
     * @return
     */
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(@RequestParam(required = true) String sceneNo,
                               @RequestParam(defaultValue = "0", required = false) Long scardId,
                               @RequestParam(defaultValue = "", required = false) String ruleName,
                               @RequestParam(defaultValue = "0", required = false) int editTab) {
        ModelAndView mView = MvUtils.getView("/scard/SCardCenter");
        LsManager.getInstance().check();
        ResultCode resultCode = ResultCode.SUCCESS;
        if (scardId == 0) {//新增
            SCard scard = new SCard();
            scard.setSceneNo(sceneNo);
            scard.setStatus(Constants.STATUS_DRAFT);
            scard.setScardNo(null);
            scard.setPercentageFlag(0);
            mView.addObject("scard", scard);
            mView.addObject("ruleList", Lists.newArrayList());
        } else {//更新
            SCard scard = scardDefinitionService.selectById(scardId);
            if (scard == null) {
                resultCode = ResultCode.NO_DATA;
            } else {
                mView.addObject("scard", scard);
                List<SCardParamVo> resultList = scardParamService.getParamsGroupByType(scardId);
                mView.addObject("scardParamsList", resultList);
                mView.addObject("resultRuleList", scardRuleService.getRulesBySCard(scardId));
            }
        }
        mView.addObject("ruleName", ruleName);
        mView.addObject("res", resultCode);
        mView.addObject("editTab", editTab);
        return mView;
    }

    @RequestMapping("/insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(SCard scard) {
        if ( scard.getWeightFlag() == null ){
            scard.setWeightFlag(0);
        }
        try {
            if (scardDefinitionService.exists(scard.getSceneNo(), scard.getWeightValue(), scard.getId(), scard.getName())) {
                return MvUtils.formatJsonResult(new ResultCode(-1, "该场景" + scard.getSceneNo() + "下已经存在相同执行顺序的策略，或有相同名称的策略"));
            }
            if (scard.getId() == null) {
                return MvUtils.formatJsonResult(scardDefinitionService.insert(scard), "policyId:" + scard.getId(), "sceneNo:" + scard.getSceneNo());
            }
        } catch (Exception e) {
            logger.error(e);
            return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
        }
        return MvUtils.formatJsonResult(scardDefinitionService.update(scard), "policyId:" + scard.getId(), "sceneNo:" + scard.getSceneNo());
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestParam long id,
                                            @RequestParam int toStatus) {
        if (id <= 0 || toStatus < 1) {
            return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
        }
        if ((toStatus == Constants.STATUS_DISCARD || toStatus == Constants.STATUS_DRAFT) && scardRouterService.existPolicy(id)) {
    		return MvUtils.formatJsonResult(new ResultCode(-10, "评分卡被评分卡路由引用，不能删除！"));
        }
        if ( ( toStatus == Constants.STATUS_DISCARD ) && scardParamService.getSCardParams(id).size() > 0 ){
            return MvUtils.formatJsonResult(new ResultCode(-10, "评分卡已经配置参数了,不能删除！"));
        }
        try {
            scardDefinitionService.updateStatus(id, toStatus);
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @ResponseBody
    @RequestMapping("/delById")
    public Map<String, Object> delById(@RequestParam(value = "id", required = true) long id) {
        if( !scardParamService.getSCardParams(id).isEmpty() ){
            return MvUtils.formatJsonResult(new ResultCode(-1, "该评分卡配置了评分参数或者评分结果规则,不能删除"));
        }else{
            try {
                scardDefinitionService.delById(id);
                return MvUtils.formatJsonResult(ResultCode.SUCCESS);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @ResponseBody
    @RequestMapping("/cloneScard")
    public Map<String, Object> cloneScard(@RequestParam(value = "scardId", required = true) Long scardId) {
        try{
            scardDefinitionService.cloneScard(scardId);
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

}
