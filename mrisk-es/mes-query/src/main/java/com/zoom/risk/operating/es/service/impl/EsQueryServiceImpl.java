package com.zoom.risk.operating.es.service.impl;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.zoom.risk.operating.es.vo.EventInputModel;
import com.zoom.risk.operating.es.vo.EventRuleInfo;
import com.zoom.risk.platform.es.service.EsClientService;
import com.zoom.risk.platform.es.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.zoom.risk.operating.es.service.EsQueryService;
import com.zoom.risk.operating.es.vo.EventOutputModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("esQueryService")
public class EsQueryServiceImpl implements EsQueryService {
	private static final Logger logger = LogManager.getLogger(EsQueryServiceImpl.class);
	private static final String type = "event"; 
	private static final String prefix = "risk-";
	private static final String suffix= "01";

	@Resource(name="esClientService")
	private EsClientService esClientService;


	@Override
	public EventOutputModel queryEvents(EventInputModel eventInputModel) {
		Client client = esClientService.getClient();
		int start = (eventInputModel.getCurrentPage()-1)*eventInputModel.getPageSize();
		String startIndex = eventInputModel.getStartRiskDate().substring(0, 7).replaceAll("-", "");
		String endIndex= eventInputModel.getEndRiskDate().substring(0, 7).replaceAll("-", "");
		String scene = eventInputModel.getSceneNo();
		String uid = eventInputModel.getUid();
		String decisionCode = eventInputModel.getDecisionCode();
		String platform = eventInputModel.getPlatform();
		String ruleNo = eventInputModel.getRuleNo();
		QueryBuilder riskDateBuilder = QueryBuilders.rangeQuery("riskDate")
													.gte(eventInputModel.getStartRiskDate())
													.lte(eventInputModel.getEndRiskDate());
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(riskDateBuilder);
		//queryBuilder.must(QueryBuilders.rangeQuery("takingTime").gt(0));
		if(!Utils.isEmpty(scene)){
			queryBuilder.must(QueryBuilders.termsQuery("scene",scene+suffix));
		}
		if(!Utils.isEmpty(uid)){
			BoolQueryBuilder orBuilder = QueryBuilders.boolQuery();
			TermsQueryBuilder uidQuery = QueryBuilders.termsQuery("uid",uid);
			TermsQueryBuilder mobileQuery = QueryBuilders.termsQuery("mobile",uid);
			TermsQueryBuilder idNumberQuery = QueryBuilders.termsQuery("idNumber",uid);
			orBuilder = orBuilder.should(uidQuery).should(mobileQuery).should(idNumberQuery);
			queryBuilder.must(orBuilder);
		}
		if(!Utils.isEmpty(decisionCode)){
			queryBuilder.must(QueryBuilders.termsQuery("decisionCode",decisionCode));
		}
		if(!Utils.isEmpty(platform)){
			queryBuilder.must(QueryBuilders.termsQuery("platform",platform));
		}
		if(!Utils.isEmpty(ruleNo)){
			queryBuilder.must(QueryBuilders.termsQuery("hitRules.ruleNo",ruleNo));
		}
		Map<String,String> extend = eventInputModel.getExtendMap();
		for( String key: extend.keySet() ){
			String value = extend.get(key);
			if( !Utils.isEmpty(value) ){
				queryBuilder.must(QueryBuilders.termsQuery(key,value));
			}
		}
		SearchRequestBuilder requestBuilder = client.prepareSearch()
													.setTypes(type)
													.setFrom(start)
													.setSize(eventInputModel.getPageSize())
													.addSort("riskDate",SortOrder.DESC);
		  
		if(startIndex.equals(endIndex)){
			requestBuilder = requestBuilder.setIndices(prefix+startIndex);
		}else{
			requestBuilder = requestBuilder.setIndices(prefix+startIndex, prefix+endIndex);
		}
		SearchResponse response = requestBuilder.setQuery(queryBuilder).execute().actionGet();
		SearchHits hits = response.getHits();
		EventOutputModel eventResult = new EventOutputModel(hits.totalHits());
		SearchHit hitArray[] = hits.getHits();
		if ( hitArray != null ) {
			for (SearchHit hit : hitArray) {
				eventResult.addHit(hit.getSource());
			}
		}
		return eventResult;
	}
	
	@Override
	/**
	 * @param sceneNo  4 digital, like 0101
	 * @param riskDate  format yyyy-MM-dd
	 */
	public EventRuleInfo queryRuleDetail(String sceneNo, String riskDate) {
		String scene = sceneNo + suffix ;
		EventRuleInfo eventRuleInfo = new EventRuleInfo();
		Client client = esClientService.getClient();
		String startDate = riskDate +" 00:00";
		String endDate = riskDate +" 23:59:59";
		SearchRequestBuilder requestBuilder = client.prepareSearch()
													.setTypes("event")
													.setSearchType(SearchType.DEFAULT);
		
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
												 .must(QueryBuilders.termQuery("scene", scene))
												 .must(QueryBuilders.rangeQuery("riskDate").gte(startDate).lte(endDate));
												 //.must(QueryBuilders.rangeQuery("takingTime").gt(0));
		TermsBuilder ruleBuilder = AggregationBuilders.terms("sceneCount").field("scene");
		ruleBuilder.subAggregation(AggregationBuilders.terms("ruleAgg").field("hitRules.ruleNo"));
		String dateString = riskDate.substring(0,7).replaceAll("-", "");
		SearchResponse response = requestBuilder.setIndices(prefix + dateString )
											    .setQuery(queryBuilder)
											    .addAggregation(ruleBuilder)
											    .execute()
											    .actionGet();
		StringTerms countAggs = (StringTerms)response.getAggregations().get("sceneCount");
		Terms.Bucket bucket = countAggs.getBucketByKey(scene);
		if ( bucket != null ){
			eventRuleInfo.setSceneNo(sceneNo);
			eventRuleInfo.setEventTotalCount(bucket.getDocCount());
			StringTerms ruleAgg = (StringTerms)bucket.getAggregations().get("ruleAgg");
			List<Bucket> ruleList = ruleAgg.getBuckets();
			ruleList.forEach(
				(ruleBucket)->{
					EventRuleInfo.RuleInfo ruleInfo = new EventRuleInfo.RuleInfo();
					ruleInfo.setRuleNo(ruleBucket.getKeyAsString());
					ruleInfo.setHitCount(ruleBucket.getDocCount());
					eventRuleInfo.add(ruleInfo);
				}
			);
		}
		return eventRuleInfo;
	}

	@Override
	public Map<String, Object> queryEventDetail(String riskDate,String riskId) {
		Client client = esClientService.getClient();
		String index = prefix+riskDate.substring(0, 7).replace("-", "");
		GetRequestBuilder requestBuilder = client.prepareGet(index, type, riskId );
		GetResponse getResponse = null;
		try {
			getResponse = requestBuilder.execute().get();
			if ( !getResponse.isExists() ){
				getResponse = client.prepareGet(index, type, riskId +"_2").execute().get();
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return getResponse.getSource();
	}


	public EventRuleInfo queryRelationship(String parameterName, String parameterValue, String riskDate){
		EventRuleInfo eventRuleInfo = new EventRuleInfo();
		Client client = esClientService.getClient();
		SearchRequestBuilder requestBuilder = client.prepareSearch().setSearchType(SearchType.DEFAULT);
		Timestamp time = new Timestamp(Clock.systemUTC().millis());
		if ( riskDate != null && (!"".equals(riskDate))) {
			time = Timestamp.valueOf(riskDate);
		}
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time.getTime());
		String endDate  = ( time  + "").substring(0,20);
		ca.add(Calendar.DAY_OF_YEAR, -32);
		String startDate  = ( new java.sql.Timestamp(ca.getTime().getTime())  + "").substring(0,20);
		if (  ! ( "uid".equals(parameterName)  || "deviceFingerprint".equals(parameterName) ) ){
			throw new RuntimeException("Input parameter is invalid, the name should be uid or deviceFingerprint ");
		}
		String term = "uid";
		if ( parameterName.equals("uid")){
			term = "deviceFingerprint";
		}
		QueryBuilder builder = QueryBuilders.termQuery(parameterName, parameterValue);

		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must( builder )
				.must(QueryBuilders.rangeQuery("riskDate").gt(startDate).lt(endDate));

		TermsBuilder ruleBuilder = AggregationBuilders.terms( term+"Count").field(term);
		SearchResponse response = requestBuilder
				.setIndices(prefix + startDate.replaceAll("-", "").substring(0,6), prefix + endDate.replaceAll("-", "").substring(0,6) )
				.setTypes("event")
				.setQuery(queryBuilder)
				.addAggregation(ruleBuilder)
				.execute()
				.actionGet();
		StringTerms countAggs = (StringTerms)response.getAggregations().get(term+"Count");
		List<Bucket> bucketList = countAggs.getBuckets();
		bucketList.forEach(
				(ruleBucket)->{
					EventRuleInfo.RuleInfo ruleInfo = new EventRuleInfo.RuleInfo();
					ruleInfo.setRuleNo(ruleBucket.getKeyAsString());
					ruleInfo.setHitCount(ruleBucket.getDocCount());
					eventRuleInfo.add(ruleInfo);
				}
		);
		return eventRuleInfo;
	}
	
	
	

}
