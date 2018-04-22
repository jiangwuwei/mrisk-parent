package com.zoom.risk.operating.roster.dao;

import com.zoom.risk.operating.roster.vo.Roster;
import com.zoom.risk.operating.roster.vo.RosterLog;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="rosterMapper")
public interface RosterMapper {

	public void rosterBatchSave(Map<String, Object> map);

    public void rosterLogSave(List<RosterLog> list);

	public void singleInsert(Map<String, Object> map);

	public Integer findCountByRosterTypeContent(Map<String,Object> map);

    public void deleteRoster(Map<String,Object> map);

    public List<Roster> findByRosterId(Map<String,Object> map);

	public void batchInsertMap(List<Map<String,Object>> list);

    public void deleteByRosterType(int rosterBusiType);

    public List<Map<String,Object>> getSceneNo(int rosterBusiType);

    public List<Map<String,Object>> getAllScene();

    public List<Map<String,Object>> getTypeByCode(@Param("code") String code);

    public List<Map<String,Object>> getRosterOrigin(@Param("rosterBusiTypeId")int rosterBusiTypeId,@Param("rosterBusiTypeLimit")int rosterBusiTypeLimit);

    public List<Roster> getContentList(Map<String,Object> paramMap);

    public Integer getContentCount(Map<String,Object> paramMap);


    public List<Map<String,Object>> getLogList(Map<String,Object> paramMap);

    public Integer getLogCount(Map<String,Object> paramMap);

}