package com.zoom.risk.operating.roster.proxy.impl;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.roster.proxy.RosterProxyService;
import com.zoom.risk.operating.roster.service.RosterService;
import com.zoom.risk.operating.roster.vo.QueryResult;
import com.zoom.risk.operating.roster.vo.Roster;
import com.zoom.risk.platform.sharding.dbsharding.ServiceExecutor;
import com.zoom.risk.platform.sharding.dbsharding.ServiceExecutorWithoutResult;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/4/7.
 */
@Service("rosterProxyService")
public class RosterProxyServiceImpl implements RosterProxyService {

    @Resource(name="rosterService")
    private RosterService rosterService;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @Override
    public List<String> rosterBatchSave(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String operator, List<String> contents) {
        return sessionManager.runWithSession(new ServiceExecutor<List<String>>() {
                    public List<String> execute() {
                        return rosterService.rosterBatchSave(rosterBusiType, rosterOrigin, rosterType, operator, contents);
                    }
                }, DBSelector.OPERATING_MASTER_DB );
    }

	    @Override
    public void batchInsertMap(List<String> sceneNoList,int rosterType) {
        sessionManager.runWithSession(new ServiceExecutorWithoutResult(){
            public void execute() {
                rosterService.batchInsertMap(sceneNoList,rosterType);
            }
        },DBSelector.OPERATING_MASTER_DB);
    }


    @Override
    public List<Map<String,Object>> getSceneNo(int rosterType) {
        return sessionManager.runWithSession(()-> rosterService.getSceneNo(rosterType)
                ,DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public List<Map<String,Object>> getAllScene() {
        return sessionManager.runWithSession(new ServiceExecutor<List<Map<String,Object>>>(){
                    public List<Map<String, Object>> execute() {
                        return rosterService.getAllScene();

                    }
                },DBSelector.OPERATING_MASTER_DB);
    }


    public void deleteRoster(Integer rosterBusiType, String operator, List<Long> ids){
        sessionManager.runWithSession(()-> rosterService.deleteRoster(rosterBusiType, operator, ids), DBSelector.OPERATING_MASTER_DB );
    }



    public List<Map<String,Object>> getTypeByCode(String code){
        return sessionManager.runWithSession(()-> rosterService.getTypeByCode(code), DBSelector.OPERATING_MASTER_DB );
    }

    public List<Map<String,Object>> getRosterOrigin(int rosterBusiTypeId){
        return sessionManager.runWithSession(()-> rosterService.getRosterOrigin(rosterBusiTypeId),DBSelector.OPERATING_MASTER_DB);
    }

    public QueryResult<Roster> getContentList(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String content, Integer currentPage, Integer pageSize){
        return sessionManager.runWithSession(()-> rosterService.getContentList(rosterBusiType,rosterOrigin,rosterType,content,currentPage,pageSize),DBSelector.OPERATING_MASTER_DB);
    }

    public QueryResult<Map<String,Object>> getLogList(Integer rosterBusiType, Integer rosterType, String content, Integer currentPage, Integer pageSize ){
        return sessionManager.runWithSession(()-> rosterService.getLogList(rosterBusiType, rosterType, content,currentPage,pageSize),DBSelector.OPERATING_MASTER_DB);
    }

}
