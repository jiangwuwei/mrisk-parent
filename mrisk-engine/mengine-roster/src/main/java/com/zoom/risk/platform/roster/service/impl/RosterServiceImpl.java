package com.zoom.risk.platform.roster.service.impl;

import com.zoom.risk.platform.roster.vo.Rosters;
import com.zoom.risk.platform.roster.service.RosterService;
import com.zoom.risk.platform.roster.vo.Restriction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangyulin on 2015/4/5.
 */
@Service("rosterService")
public class RosterServiceImpl implements RosterService {
	private static final Logger logger = LogManager.getLogger(RosterServiceImpl.class);

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public int verifyHitRoster(Integer rosterBusiType, List<Rosters> queryList){
		String sql = " select count(1) as cn from zoom_bi_roster_" + rosterBusiType+ " where ";
		final StringBuffer buffer = new StringBuffer();
		queryList.forEach(
			(rosters)->{
				buffer.append(" or ( content = '" + rosters.getValue().trim() + "' and roster_type = " + rosters.getDbValue() + " ) " );
			}
		);
		sql = sql + buffer.delete(0, 4).toString();
		logger.info("Roster sql:{}",sql);
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
		int cn = 0;
		if ( rowSet.next() ){
			cn = rowSet.getInt("cn");
			if ( cn > 0 ){
				logger.info("Roster is hit with rosterBusiType {}, sql : {}", rosterBusiType, sql);
			}
		}
		return cn;
	}

	@Transactional(readOnly = true)
	public List<Restriction> getSceneAppliedRoster(){
		List<Restriction> resultList = new ArrayList<>();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select roster_busi_type, scene_no from zoom_bi_roster_restriction order by roster_busi_type");
		while ( rowSet.next() ) {
			Restriction r = new Restriction(rowSet.getInt("roster_busi_type"), rowSet.getString("scene_no") );
			resultList.add(r);
		}
		return resultList;
	}

	@Transactional(readOnly = true)
	public Timestamp findLastSceneAppliedRoster(){
		Timestamp lastTime = null;
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select created_date  from zoom_bi_roster_restriction order by created_date desc limit 1");
		if ( rowSet.next() ){
			lastTime = rowSet.getTimestamp("created_date");
		}
		return lastTime;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
