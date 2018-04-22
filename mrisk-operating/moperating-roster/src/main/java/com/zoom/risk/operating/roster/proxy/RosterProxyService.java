package com.zoom.risk.operating.roster.proxy;

import com.zoom.risk.operating.roster.vo.QueryResult;
import com.zoom.risk.operating.roster.vo.Roster;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/4/7.
 */
public interface RosterProxyService {

    public List<String> rosterBatchSave(Integer dbType, Integer rosterOrigin, Integer rosterType, String creator, List<String> contents);

	public void batchInsertMap(List<String> sceneNoList,int rosterBusiType);

    public List<Map<String,Object>> getSceneNo(int rosterBusiType);

    public List<Map<String,Object>> getAllScene();

    public List<Map<String,Object>> getTypeByCode(String code);

    public void deleteRoster(Integer rosterBusiType, String creator, List<Long> ids);

    public List<Map<String,Object>> getRosterOrigin(int rosterBusiTypeId);

    public QueryResult<Roster> getContentList(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String content,Integer currentPage,Integer pageSize );

    public QueryResult<Map<String,Object>> getLogList(Integer rosterBusiType, Integer rosterType, String content, Integer currentPage, Integer pageSize );

}
