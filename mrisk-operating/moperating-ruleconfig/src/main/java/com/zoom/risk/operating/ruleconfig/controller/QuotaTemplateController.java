package com.zoom.risk.operating.ruleconfig.controller;

import com.zoom.risk.operating.ruleconfig.model.QuotaTemplate;
import com.zoom.risk.operating.ruleconfig.service.QuotaTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liyi8 on 2017/3/20.
 */
@Controller
@RequestMapping("/quotaTemplate")
public class QuotaTemplateController {

    @Autowired
    private QuotaTemplateService quotaTemplateService;

    @RequestMapping("getTemplateById")
    @ResponseBody
    public QuotaTemplate getTemplateById(@RequestParam long id){
       /* HashMap<String,Object> map = new HashMap<>();
        map.put("quotaTemplate",quotaTemplateService.selectById(id));*/
       return quotaTemplateService.selectById(id);
//        return  map;
    }
}
