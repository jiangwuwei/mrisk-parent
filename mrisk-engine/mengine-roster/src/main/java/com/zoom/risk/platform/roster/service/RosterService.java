package com.zoom.risk.platform.roster.service;

import com.zoom.risk.platform.roster.vo.Rosters;
import com.zoom.risk.platform.roster.vo.Restriction;

import java.sql.Timestamp;
import java.util.List;

public interface RosterService {

	/**
	 * @param rosterBusiType  应用于那些名单库类型
	 * @param queryList       查询条件
	 * @return
	 */
	public int verifyHitRoster(Integer rosterBusiType, List<Rosters> queryList);


	public List<Restriction> getSceneAppliedRoster();

	public Timestamp findLastSceneAppliedRoster();

}
