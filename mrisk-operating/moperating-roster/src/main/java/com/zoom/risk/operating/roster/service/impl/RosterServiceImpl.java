package com.zoom.risk.operating.roster.service.impl;

import com.zoom.risk.operating.roster.dao.RosterMapper;
import com.zoom.risk.operating.roster.service.RosterService;
import com.zoom.risk.operating.roster.vo.QueryResult;
import com.zoom.risk.operating.roster.vo.Roster;
import com.zoom.risk.operating.roster.vo.RosterCredit;
import com.zoom.risk.operating.roster.vo.RosterLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/4/7.
 */
@Service("rosterService")
public class RosterServiceImpl implements RosterService {
    private static final Logger logger = LogManager.getLogger(RosterServiceImpl.class);

    @Resource(name="rosterMapper")
    private RosterMapper rosterMapper;

    @Transactional
    public List<String> rosterBatchSave(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String operator, List<String> contents){
        List<Roster> list = new ArrayList<>();
        List<RosterLog> listLog = new ArrayList<>();
        List<String> existList = new ArrayList<>();
        contents.forEach(
            (content)->{
                RosterLog log = new RosterLog(rosterBusiType, RosterLog.OPER_TYPE_ADD, rosterOrigin, rosterType, content, operator, RosterLog.STATUS_SUCCESS, RosterLog.STATUS_DESC_SUCCESS);
                Integer count = RosterServiceImpl.this.findCountByRosterTypeContent(rosterBusiType, rosterType, content);
                if ( count > 0 ){
                    existList.add(content);
                    log.setStatus(RosterLog.STATUS_FAIL);
                    log.setStatusDesc(RosterLog.STATUS_DESC_FAIL);
                }else {
                    Roster roster = new Roster(rosterOrigin, rosterType, content, operator);
                    list.add(roster);
                }
                listLog.add(log);
            }
        );
        if ( !list.isEmpty() ) {
            Map<String, Object> map = new HashMap<>();
            map.put("suffix", rosterBusiType);
            map.put("list", list);
            rosterMapper.rosterBatchSave(map);
        }
        rosterMapper.rosterLogSave(listLog);
        return existList;
    }

    @Transactional
    public void deleteRoster(Integer rosterBusiType, String operator,  List<Long> ids){
        Map<String, Object> map = new HashMap<>();
        map.put("suffix", rosterBusiType);
        map.put("list", ids);
        List<Roster> list = rosterMapper.findByRosterId(map);
        List<RosterLog> listLog = new ArrayList<>();
        for(Roster r : list ){
            RosterLog log = new RosterLog(rosterBusiType, RosterLog.OPER_TYPE_DEL, r.getRosterOrigin(), r.getRosterType(), r.getContent(), operator , RosterLog.STATUS_SUCCESS, RosterLog.STATUS_DESC_SUCCESS);
            listLog.add(log);
        }
        rosterMapper.rosterLogSave(listLog);
        rosterMapper.deleteRoster(map);
    }


    private List<RosterCredit> convert(List<RosterLog> list, int operType){
        List<RosterCredit> resultList = new ArrayList<>();
        for(RosterLog log : list ){
            if (log.getRosterType() == Roster.ROSTER_DB_UID ) {
                resultList.add(new RosterCredit(log.getContent(), operType, RosterCredit.SOURCE_TYPE_EUI_1));
            }
        }
        return resultList;
    }


    @Transactional(readOnly = true)
    public Integer findCountByRosterTypeContent(Integer rosterBusiType, Integer rosterType, String content){
        Map<String,Object> map = new HashMap<>();
        map.put("suffix",rosterBusiType);
        map.put("rosterType",rosterType);
        map.put("content",content);
        return rosterMapper.findCountByRosterTypeContent(map);
    }

	@Override
    @Transactional
    public void batchInsertMap(List<String> sceneNoList,int rosterBusiType) {
        if(sceneNoList==null){
            rosterMapper.deleteByRosterType(rosterBusiType);
        }else{
            rosterMapper.deleteByRosterType(rosterBusiType);
            List<Map<String,Object>> list = new ArrayList<>();
            for(String sceneNo : sceneNoList){
                Map<String,Object> map = new HashMap<>();
                map.put("sceneNo",sceneNo);
                map.put("rosterBusiType",rosterBusiType);
                list.add(map);
            }
            rosterMapper.batchInsertMap(list);
        }
    }
    @Transactional(readOnly = true)
    public QueryResult<Roster> getContentList(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String content, int currentPage, int pageSize){
        Map<String,Object> paramMap = new HashMap<>();
        QueryResult<Roster> queryResult = new QueryResult<>();
        paramMap.put("suffix", rosterBusiType);
        paramMap.put("rosterOrigin", rosterOrigin);
        paramMap.put("content", content);
        paramMap.put("rosterType", rosterType);
        int offset = (currentPage-1)*pageSize;
        paramMap.put("limit",pageSize);
        paramMap.put("offset",offset);
        List<Roster> list = rosterMapper.getContentList(paramMap);
        Integer contentCount = rosterMapper.getContentCount(paramMap);
        queryResult.setList(list);
        queryResult.setTotalCount(contentCount);
        return queryResult;
    }

    @Transactional(readOnly = true)
    public QueryResult<Map<String,Object>> getLogList(Integer rosterBusiType, Integer rosterType, String content, Integer currentPage, Integer pageSize ){
        Map<String,Object> paramMap = new HashMap<>();
        QueryResult<Map<String,Object>> queryResult = new QueryResult<>();
        paramMap.put("rosterBusiType", rosterBusiType);
        paramMap.put("rosterType", rosterType);
        paramMap.put("content", content);
        int offset = (currentPage-1)*pageSize;
        paramMap.put("limit",pageSize);
        paramMap.put("offset",offset);
        List<Map<String,Object>> list = rosterMapper.getLogList(paramMap);
        Integer contentCount = rosterMapper.getLogCount(paramMap);
        queryResult.setList(list);
        queryResult.setTotalCount(contentCount);
        return queryResult;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String,Object>> getSceneNo(int rosterBusiType) {
        return rosterMapper.getSceneNo(rosterBusiType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String,Object>> getAllScene() {
        return rosterMapper.getAllScene();
    }

    @Transactional(readOnly = true)
    public List<Map<String,Object>> getTypeByCode(String code){
        return rosterMapper.getTypeByCode(code);
    }

    @Transactional(readOnly = true)
    public List<Map<String,Object>> getRosterOrigin(int rosterBusiTypeId){
        return rosterMapper.getRosterOrigin(rosterBusiTypeId,rosterBusiTypeId+100);
    }
}
