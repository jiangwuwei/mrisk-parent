package com.zoom.risk.operating.scard.controller;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.scard.model.JadeMode;
import com.zoom.risk.operating.scard.model.JadeModeDefinition;
import com.zoom.risk.operating.scard.model.JadeModeVo;
import com.zoom.risk.operating.scard.proxy.JadeServiceProxy;
import com.zoom.risk.operating.scard.service.SCardParamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/jadeParams")
public class JadeParamsController {
    private static final Logger logger = LogManager.getLogger(JadeParamsController.class);

    @Resource
    private JadeServiceProxy jadeServiceProxy;

    @Resource
    private SCardParamService scardParamService;

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value="offset", required=false,defaultValue="0") int offset,
                             @RequestParam(value="limit",required=false,defaultValue="0") int limit){
        ModelAndView mView = MvUtils.getView("/scard/JadeParams");
        List<JadeModeVo> jadeModeList = jadeServiceProxy.findAllParams();
        mView.addObject("limit", limit);
        mView.addObject("offset", offset);
        mView.addObject("jadeModeList", jadeModeList);
        return mView;
    }

    @RequestMapping("/saveBusiType")
    @ResponseBody
    public Map<String, Object> saveBusiType(JadeMode jadeMode) {
        try {
            jadeServiceProxy.insertMode(jadeMode);
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @RequestMapping("/delBusiType")
    @ResponseBody
    public Map<String, Object> delBusiType(@RequestParam(value="busiTypeId", required=true) int busiTypeId) {
        try {
            jadeServiceProxy.deleteMode(busiTypeId);
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @RequestMapping("/delDefinition")
    @ResponseBody
    public Map<String, Object> delDefinition(@RequestParam(value="definitionId", required=true) Long definitionId) {
        try {
            JadeModeDefinition definition = jadeServiceProxy.selectDefinitionByPrimaryKey(definitionId);
            //不能删除已经被引用的参数
            if ( scardParamService.alreadyRefered(definition.getParamName()) ){
                throw new Exception("参数【" + definition.getParamName() + "】已经被评分卡引用");
            }else{
                jadeServiceProxy.deleteDefinition(definitionId);
            }
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @RequestMapping("/saveDefinition")
    @ResponseBody
    public Map<String, Object> saveDefinition(JadeModeDefinition definition) {
        try {
            if ( definition.getId() != null && definition.getTypeId() != null ){
                JadeModeDefinition oldOne = jadeServiceProxy.selectDefinitionByPrimaryKey(definition.getId());
                //不能修改已经被引用的参数
                if ( oldOne != null ){
                    if (scardParamService.alreadyRefered(oldOne.getParamName())) {
                        throw new Exception("参数【" + oldOne.getParamName() + "】已经被评分卡引用");
                    }
                    definition.setDbName(definition.getParamName().toLowerCase());
                    jadeServiceProxy.updateDefinition(definition);
                }
            }else{
                if ( definition.getTypeId()  == 0 ){
                    definition.setLength(64);
                }else if ( definition.getTypeId() == 4 ){
                    definition.setLength(14);
                    definition.setDecimalPlace(2);
                }
                definition.setDbName(definition.getParamName().toLowerCase());
                jadeServiceProxy.insertDefinition(definition);
            }
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }


}
