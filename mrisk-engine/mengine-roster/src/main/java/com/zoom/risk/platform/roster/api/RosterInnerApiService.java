package com.zoom.risk.platform.roster.api;

import com.zoom.risk.platform.roster.vo.Rosters;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/4/5.
 */
public interface RosterInnerApiService {

    public Map<String,Object> innerVerifyHitRoster(String sceneNo,List<Rosters> list);

    public Map<String,Object> verifyHitRoster(Integer rosterBusiType, List<Rosters> list);

    public void refresh();

}
