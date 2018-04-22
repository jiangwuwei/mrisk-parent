package com.zoom.risk.platform.decision.service;

import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.vo.QutoaInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/16.
 */
public interface DTreeExecutorService {

    public void executeDecisionTree(DBNode node, Map<String,Object> context, List<DBNode> routes, List<QutoaInfo> quotaValueList, String scene4no);

}
