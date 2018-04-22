package com.zoom.risk.operating.decision.service;

import com.zoom.risk.operating.decision.vo.EchartsTreeVo;

import java.util.List;

/**
 * Created by liyi8 on 2017/5/30.
 */
public interface EchartsTreeVoService {

    /**
     * 构建决策树配置处决策树
     * @param policyId
     * @return
     */
    EchartsTreeVo buildTree(Long policyId);

    /**
     * 构建事件中心决策树
     * @param policyId
     * @param hitNodeIdList
     * @return
     */
    EchartsTreeVo buildEventTree(Long policyId, List<String> hitNodeIdList);
}
