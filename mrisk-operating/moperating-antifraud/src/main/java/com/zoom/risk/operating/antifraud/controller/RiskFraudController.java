package com.zoom.risk.operating.antifraud.controller;

import com.zoom.risk.operating.antifraud.proxy.RiskFraudProxyService;
import com.alibaba.fastjson.JSON;
import com.zoom.risk.operating.antifraud.vo.Device;
import com.zoom.risk.operating.antifraud.vo.Event;
import com.zoom.risk.operating.antifraud.vo.EventQuantity;
import com.zoom.risk.platform.common.util.Utils;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

@RestController
@RequestMapping("/riskFraud")
public class RiskFraudController {
    private static final Logger logger = LogManager.getLogger(RiskFraudController.class);

    @Resource(name = "riskFraudProxyService")
    private RiskFraudProxyService riskFraudProxyService;

    @RequestMapping("/eventQuantityToday")    //当RiskFraudProxyService天事件决策统计结果
    public List<EventQuantity> getEventQuantity(@RequestParam(value = "platform", required = false) String platform) {
        LsManager.getInstance().check();
        List<EventQuantity> list = riskFraudProxyService.getEventQuantityToday(platform);
        int totalCount = 0;
        for (EventQuantity eventQuantity : list) {
            if ("4".equals(eventQuantity.getDecisionCode())) {
                totalCount = eventQuantity.getQuantity();
                break;
            }
        }
        for (EventQuantity eventQuantity : list) {
            double ratio = 0;
            if(totalCount != 0){
                ratio = new BigDecimal((eventQuantity.getQuantity() * 100.0) / totalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            eventQuantity.setRatio(ratio);
        }
        return list;
    }

    @RequestMapping("/deviceQuantity")    //近七天设备获取情况统计结果
    public Map<String, Object> getDeviceQuantity(@RequestParam(value = "queryDate", required = false) String queryDate,
                                                 @RequestParam(value = "scene", required = false) String scene,
                                                 @RequestParam(value = "platform", required = false) String platform) {
        Map<String, Object> paramMap = new HashMap<>();
        if (Utils.isEmpty(queryDate)) {
            queryDate = DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyy-MM-dd");
        }
        paramMap.put("startDate", queryDate + " 00:00:00");
        paramMap.put("endDate", queryDate + " 59:59:59");
        if (Utils.isNotEmpty(scene)) {
            paramMap.put("scene", scene);
        }
        if (Utils.isNotEmpty(platform)) {
            paramMap.put("platform", platform);
        }
        List<Device> deviceList = riskFraudProxyService.getDeviceQuantity(paramMap);
        logger.info("deviceList:" + JSON.toJSONString(deviceList));

        List<String> dateList = new ArrayList<>();    //近七天每天设备获取情况包含获取率
        List<Integer> successList = new ArrayList();
        List<Integer> failList = new ArrayList();
        for (Device d : deviceList) {    //遍历设备情况list，将日期、获取成功、失败量按顺序分别添加到各个list里
            dateList.add(d.getDateStr());
            successList.add(d.getSuccessCount());
            failList.add(d.getFailCount());
        }
        String[] dateArr = dateList.toArray(new String[dateList.size()]);    //日期数组
        Integer[] successCountArr = successList.toArray(new Integer[successList.size()]);    //近七天每天获取设备成功量
        Integer[] failCountArr = failList.toArray(new Integer[failList.size()]);    //近七天每天获取设备失败量
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("dateArr", dateArr);
        resultMap.put("successCountArr", successCountArr);
        resultMap.put("failCountArr", failCountArr);
        resultMap.put("list", deviceList);
        logger.info("近N天设备获取情况统计结果:" + JSON.toJSONString(resultMap));
        return resultMap;
    }

    @RequestMapping("/eventQuantity")    //近七天事件决策统计结果
    public Map<String, Object> getEventQuantity(@RequestParam(value = "queryDate", required = false) String queryDate,
                                                @RequestParam(value = "scene", required = false) String scene,
                                                @RequestParam(value = "platform", required = false) String platform) {
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (Utils.isEmpty(queryDate)) {
            queryDate = DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyy-MM-dd");
        }
        paramMap.put("startDate", queryDate + " 00:00:00");
        paramMap.put("endDate", queryDate + " 59:59:59");
        if (Utils.isNotEmpty(scene)) {
            paramMap.put("scene", scene);
        }
        if (Utils.isNotEmpty(platform)) {
            paramMap.put("platform", platform);
        }
        List<Event> eventList = riskFraudProxyService.getEventQuantity(paramMap);
        List<String> dateList = new ArrayList<>();
        List<Integer> passList = new ArrayList<>();
        List<Integer> verifyList = new ArrayList<>();
        List<Integer> refuseList = new ArrayList<>();
        List<Integer> totalList = new ArrayList<>();
        for (Event e : eventList) {    //遍历时间决策统计list，将日期、获取成功、失败量按顺序分别添加到各个list里
            dateList.add(e.getDateStr());
            passList.add(e.getPassCount());
            verifyList.add(e.getVerifyCount());
            refuseList.add(e.getRefuseCount());
            totalList.add(e.getTotalCount());
        }
        String[] dateArr = dateList.toArray(new String[dateList.size()]);    //日期数组
        Integer[] passCountArr = passList.toArray(new Integer[passList.size()]);    //近七天每天通过量数组
        Integer[] verifyCountArr = verifyList.toArray(new Integer[verifyList.size()]);    //近七天每天人工审核量数组
        Integer[] refuseCountArr = refuseList.toArray(new Integer[refuseList.size()]);    //近七天每天拒绝量数组
        Integer[] totalCountArr = totalList.toArray(new Integer[totalList.size()]);    //近七天每天总量数组
        int passTotalCount = 0;    //近七天通过总量
        int verifyTotalCount = 0;    //近七天人工审核总量
        int refuseTotalCount = 0;    //近七天拒绝总量
        int totalCount = 0;    //近七天总事件量
        for (int i = 0; i < passCountArr.length; i++) {
            passTotalCount = passTotalCount + passCountArr[i];
            verifyTotalCount = verifyTotalCount + verifyCountArr[i];
            refuseTotalCount = refuseTotalCount + refuseCountArr[i];
            totalCount = totalCount + totalCountArr[i];
        }
        double passRatio = totalCount==0?0:new BigDecimal((passTotalCount * 100.0) / totalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    //近七天通过比率
        double verifyRatio = totalCount==0?0:new BigDecimal((verifyTotalCount * 100.0) / totalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    //近七天人工审核比率
        double refuseRatio = totalCount==0?0:new BigDecimal((refuseTotalCount * 100.0) / totalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    //近七天拒绝比率
        Map<String, Object> rateMap = new HashMap<>();
        rateMap.put("passTotalCount", passTotalCount);
        rateMap.put("verifyTotalCount", verifyTotalCount);
        rateMap.put("refuseTotalCount", refuseTotalCount);
        rateMap.put("totalCount", totalCount);
        rateMap.put("passRatio", passRatio);
        rateMap.put("verifyRatio", verifyRatio);
        rateMap.put("refuseRatio", refuseRatio);
        resultMap.put("dateArr", dateArr);
        resultMap.put("passCountArr", passCountArr);
        resultMap.put("verifyCountArr", verifyCountArr);
        resultMap.put("refuseCountArr", refuseCountArr);
        resultMap.put("rateMap", rateMap);
        logger.info("近N天事件决策统计结果:" + JSON.toJSONString(resultMap));
        return resultMap;
    }

}
