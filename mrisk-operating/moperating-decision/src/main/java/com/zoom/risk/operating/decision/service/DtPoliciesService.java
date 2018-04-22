package com.zoom.risk.operating.decision.service;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.decision.po.TPolicies;
import com.zoom.risk.operating.decision.po.TPolicy;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/5/24.
 */
public interface DtPoliciesService {

    public List<TPolicies> selectAll();

    public TPolicies selectById(String sceneNo);

    public List<TPolicies> selectPage(String sceneNo, int offset, int limit);

    public int selectCount(String sceneNo);

    public boolean exists(String sceneNo, String name) throws Exception;

    public boolean update(TPolicies TPolicies);

    public ResultCode delById(String sceneNo);

    public boolean insert(TPolicies TPolicies);

    public List<TPolicies> selectByName(String name, int offset, int limit);

    public List<String> getSceneNoList(List<TPolicies> policiesList, String sceneNo);

    public List<Pair<TPolicies, List<TPolicy>>> getPoliciesPairList(List<TPolicies> policiesList,
                                                                    List<TPolicy> policyList);

    public Map<String, TPolicies> getPoliciesMap(List<TPolicies> policiesList);
}
