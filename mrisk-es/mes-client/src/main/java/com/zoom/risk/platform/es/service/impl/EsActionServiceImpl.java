package com.zoom.risk.platform.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.zoom.risk.platform.es.service.EsActionService;
import com.zoom.risk.platform.es.service.EsClientService;
import com.zoom.risk.platform.es.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionWriteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
@Service("esActionService")
public class EsActionServiceImpl implements EsActionService {
	private static final Logger logger = LogManager.getLogger(EsActionServiceImpl.class);
	private static final String INDEX_TYPE ="event";
	private static final String INDEX_GATWAY_TYPE ="monitor";
	private static final String INDEX_PREFIX = "risk-";
	private static final String INDEX_MONITOR_PREFIX = "monitor-";
	private static final String RISK_DATE = "riskDate";
	private static final String RISK_STATUS = "riskStatus";
	private static final String RISK_LONG_DATE = "riskLongDate";
	private static final String RISK_ID = "riskId";
	private static final String RISK_SCENE = "scene";
	private static final String RISK_SERIAL_NUMBER = "serialNumber";
    private static final String RISK_TAKING_TIME = "takingTime";
	//
	private static final String RETRY_TIMES = "retryTimes";

	@Resource(name="esClientService")
	private EsClientService esClientService;


	@Override
	public boolean dispatchEvent(String jsonSource){
		boolean resultFlag = false;
		try{
			Map<String,Object> resultMap = JSON.parseObject(jsonSource, new TypeToken<HashMap<String,Object>>(){}.getType());
			String riskType = (String)resultMap.get("riskType");
			if ( "2".equals(riskType)){
				resultFlag = this.createOrUpdateDoc(resultMap,jsonSource);
			}else if ( "1".equals(riskType)){
				resultFlag = this.createMonitorEvent(resultMap,jsonSource);
			}
		}catch(Exception e){
			logger.error("Parse message happens error",e);
		}
		return  resultFlag;
	}


	/**
	 * event for input
	 * @param resultMap
	 * @param jsonSource
	 * @return
	 */
	protected boolean createOrUpdateDoc(Map<String,Object> resultMap, String jsonSource) {
		boolean result = false;
		try{
			String riskDate = String.valueOf(resultMap.get(RISK_DATE));
			String riskId = String.valueOf(resultMap.get(RISK_ID));
			this.handleQuotaValue(resultMap);
			String finalJsonSource = JSON.toJSONString(resultMap);
			if (riskDate.length() < 7 ){
				riskDate = ( new java.sql.Timestamp(System.currentTimeMillis())+"").substring(0,10);
			}
			String index = INDEX_PREFIX + riskDate.substring(0,7).replace("-", "");
			result = this.createIndex(index, riskId, finalJsonSource,INDEX_TYPE);
			if ( !result ){
				logger.error("Creating a doc fails , riskId:{}", riskId);
			}
		}catch(Throwable e){
			logger.error("createOrUpdateDoc happens error",e);
		}
		return result;
	}

	/**
	 * gateway monitor event for input
	 * @param resultMap
	 * @param jsonSource
	 * @return
	 */
	protected boolean createMonitorEvent(Map<String,Object> resultMap, String jsonSource) {
		long time = System.currentTimeMillis();
		boolean result = false;
		try{
			String riskDate = String.valueOf(resultMap.get(RISK_DATE));
			String riskId = String.valueOf(resultMap.get(RISK_ID));
			String index = INDEX_MONITOR_PREFIX + riskDate.substring(0,7).replace("-", "");
			result = this.createIndex(index, riskId, jsonSource, INDEX_GATWAY_TYPE);
			if ( !result ){
				logger.error("Creating a doc fails , riskId:{}", riskId);
			}
		}catch(Throwable e){
			logger.error("createOrUpdateDoc happens error",e);
		}
		return result;
	}

	/**
	 * create a doc
	 * @param index
	 * @param docId
	 * @param source
	 * @return
	 */
	protected boolean createIndex(String index, String docId, String source, String indexType) {
		Client client = esClientService.getClient();
		//logger.info("CreateIndex : before saving to es , index:{}, type: {} and json data : {}", index, indexType, source);
		IndexRequestBuilder builder = client.prepareIndex(index,  indexType , docId);
		builder.setSource(source);
		IndexResponse  response = builder.execute().actionGet();
        boolean flag = response.isCreated();
        ActionWriteResponse.ShardInfo shard = response.getShardInfo();
        if ( shard.getFailed() > 0 ){
            logger.error("Shard Info, failed count {}, successful count {}, total count {}", shard.getFailed(), shard.getSuccessful(), shard.getTotal());
        }
        if ( !flag && response.getVersion() == 1) {
            logger.error("EsActionService happens error when create a doc: " +  source);
        }
		return flag;
	}
	
	/**
	 * find and update a doc, if we don't find a doc then send it to kafka with retry times increased 1
	 * Only retry three times if still don't find then fail it
	 * @param riskId
	 * @param serialNumber
	 * @param jsonSource
	 * @return
	 */
	public boolean findAndUpdateIndex(String riskId, String serialNumber, String jsonSource, int retryTimes){
		boolean flag = false;
		SearchHits hits = this.findDocByCond(riskId, serialNumber);
		if (hits.getTotalHits() > 0) {
			SearchHit searchHit = hits.getAt(0);
			this.updateDoc(searchHit, jsonSource);
			flag = true;
		}
		return flag;
	}
	
	/**find doc according to condition
	 * @param riskId
	 * @param serialNumber
	 * @return
	 */
	private SearchHits findDocByCond(String riskId, String serialNumber ){
		Client client = esClientService.getClient();
		String current =  INDEX_PREFIX + Utils.getCurrentIndex(0);
		String beforeCurrent =  INDEX_PREFIX + Utils.getCurrentIndex(1);
		QueryBuilder qb = null;
		if ( !Utils.isEmpty(serialNumber) ){
			qb = QueryBuilders.termQuery(RISK_SERIAL_NUMBER,serialNumber);
		}
		//need to be placed in the end
		if ( !Utils.isEmpty(riskId) ){
			qb = QueryBuilders.termQuery(RISK_ID,riskId);
		}
		SearchResponse response = client.prepareSearch()
										.setIndices(current,beforeCurrent)
										.setTypes(INDEX_TYPE)
										.setSearchType(SearchType.DEFAULT)
										.setQuery(qb)
										.setFrom(0)
										.setSize(2)
										.setNoFields()
										.execute()
										.actionGet();
		logger.info("Query Result: riskId:{}, serialNumber:{},  Response:{} ", riskId, serialNumber, response );
		return response.getHits();
	}
	
	/** update a doc only by changed fields
	 * @param searchHit
	 * @param jsonSource
	 * @return
	 */
	private boolean updateDoc(SearchHit searchHit, String jsonSource){
		String id = searchHit.getId();
		String index = searchHit.getIndex();
		String type = searchHit.getType();
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> changeMap = JSON.parseObject(jsonSource, new TypeToken<HashMap<String, Object>>() {}.getType());
        changeMap.forEach((key,value)->{
            if (!Utils.isEmpty(value)) {
                if ( ! ( key.equals(RISK_ID) || key.equals(RISK_SCENE ) || key.equals(RISK_SERIAL_NUMBER) || key.equals(RETRY_TIMES) ) ) {
                    resultMap.put(key, value);
                }
            }
        });
        Client client = esClientService.getClient();
        String destSource = JSON.toJSONString(resultMap);
		logger.info("UpdateDoc : before updating to es , json data : {}", destSource);
		UpdateResponse updateResponse = client.prepareUpdate()
                .setIndex(index)
                .setType(type)
                .setId(id)
				.setDoc(destSource)
                .execute()
                .actionGet();
		return updateResponse.isCreated();
	}

	public void handleQuotaValue(Map<String, Object> map) {
		if (map.containsKey("quotasValue")) {
			List<Map<String, Object>> quotaList = (List<Map<String, Object>>) map.get("quotasValue");
			quotaList.forEach(
				(quotaMap) -> {
					if (quotaMap.get("quotaValue") != null) {
						Object quotaValue = quotaMap.get("quotaValue");
						if (quotaValue instanceof List) {
							StringBuilder builder = new StringBuilder();
							((List) quotaValue).forEach(
									(object) -> {
										builder.append(object.toString() + ",");
									}
							);
							if (builder.length() > 0) {
								builder.delete(builder.length() - 1, builder.length());
							}
							quotaValue = builder.toString();
						} else {
							quotaValue = quotaMap.get("quotaValue").toString();
						}
						quotaMap.put("quotaValueStr", quotaValue);
						quotaMap.remove("quotaValue");
					}
				}
			);
		}
	}

}
