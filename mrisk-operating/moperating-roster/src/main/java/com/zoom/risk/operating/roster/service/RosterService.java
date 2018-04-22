package com.zoom.risk.operating.roster.service;

import com.zoom.risk.operating.roster.vo.QueryResult;
import com.zoom.risk.operating.roster.vo.Roster;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/4/7.
 */
public interface RosterService {

    public List<String> rosterBatchSave(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String operator, List<String> contents);

    public Integer findCountByRosterTypeContent(Integer rosterBusiType, Integer rosterType, String content);

    public void deleteRoster(Integer rosterBusiType, String operator, List<Long> ids);

	public void batchInsertMap(List<String> sceneNoList,int rosterBusiType);

    public List<Map<String,Object>> getSceneNo(int rosterBusiType);

    public List<Map<String,Object>> getAllScene();

    public List<Map<String,Object>> getTypeByCode(String code);

    public List<Map<String,Object>> getRosterOrigin(int rosterBusiTypeId);

    public QueryResult<Roster> getContentList(Integer rosterBusiType, Integer rosterOrigin, Integer rosterType, String content, int limit, int offset);

    public QueryResult<Map<String,Object>> getLogList(Integer rosterBusiType, Integer rosterType,  String content, Integer currentPage, Integer pageSize );

}
