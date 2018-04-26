package com.zoom.risk.gateway.extend.framework;

import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.gateway.extend.common.ContextExtendedAnnotation;
import com.zoom.risk.gateway.extend.service.ContextExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
@Service("riskExtendFramework")
public class RiskExtendFrameworkImpl implements com.zoom.risk.gateway.extend.service.RiskExtendFramework, ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LogManager.getLogger(RiskExtendFrameworkImpl.class);
	private List<ContextExtension> antiFraudList = new ArrayList<>();
	private List<ContextExtension> dtreeList = new ArrayList<>();
	private List<ContextExtension> scardList = new ArrayList<>();

	@Override
	public Map<String, Object> decorate(Map<String, Object> riskInput, String riskBusiTYpe) {
		Map<String, Object> resultInput = riskInput;
		List<ContextExtension> targetList = null;
		if (RiskResult.RISK_BUSI_TYPE_ANTIFRAUD.equals(riskBusiTYpe)){
			targetList =  antiFraudList;
		}else if (RiskResult.RISK_BUSI_TYPE_DECISION_TREE.equals(riskBusiTYpe)){
			targetList = dtreeList;
		}else if (RiskResult.RISK_BUSI_TYPE_SCARD.equals(riskBusiTYpe)){
			targetList = scardList;
		}
		for( ContextExtension extendService : targetList ){
			resultInput = extendService.invoke(resultInput);
		}
		return resultInput;
	}

	public synchronized void initializedExtendList(ApplicationContext applicationContext) {
		String[] beanNames = applicationContext.getBeanNamesForAnnotation(ContextExtendedAnnotation.class);
		if (beanNames.length > 0) {
			for (String bean : beanNames) {
				Object extendBean = applicationContext.getBean(bean);
				if (extendBean instanceof ContextExtension) {
					ContextExtension extendService = (ContextExtension) extendBean;
					String[] busiTypes = extendService.getClass().getAnnotation(ContextExtendedAnnotation.class).includes();
					if ( busiTypes != null && busiTypes.length > 0 ) {
						for (String busiType : busiTypes) {
							if (busiType.equals(RiskResult.RISK_BUSI_TYPE_ANTIFRAUD)) {
								antiFraudList.add(extendService);
							} else if (busiType.equals(RiskResult.RISK_BUSI_TYPE_DECISION_TREE)) {
								dtreeList.add(extendService);
							} else if (busiType.equals(RiskResult.RISK_BUSI_TYPE_SCARD)) {
								scardList.add(extendService);
							}
						}
					}
				} else {
					logger.warn("Bean named [{}] use ContextExtendedAnnotation annotation but not implements RiskCreditExtendService interface", bean);
				}
			}
			Collections.sort(antiFraudList, (es1,es2)-> {
				ContextExtendedAnnotation extendAnnotation1 = es1.getClass().getAnnotation(ContextExtendedAnnotation.class);
				ContextExtendedAnnotation extendAnnotation2 = es2.getClass().getAnnotation(ContextExtendedAnnotation.class);
				return extendAnnotation1.order() - extendAnnotation2.order();
			});
			Collections.sort(dtreeList, (es1,es2)-> {
				ContextExtendedAnnotation extendAnnotation1 = es1.getClass().getAnnotation(ContextExtendedAnnotation.class);
				ContextExtendedAnnotation extendAnnotation2 = es2.getClass().getAnnotation(ContextExtendedAnnotation.class);
				return extendAnnotation1.order() - extendAnnotation2.order();
			});
			Collections.sort(scardList, (es1,es2)-> {
				ContextExtendedAnnotation extendAnnotation1 = es1.getClass().getAnnotation(ContextExtendedAnnotation.class);
				ContextExtendedAnnotation extendAnnotation2 = es2.getClass().getAnnotation(ContextExtendedAnnotation.class);
				return extendAnnotation1.order() - extendAnnotation2.order();
			});
			logger.info("initializedExtendList messages : antiFraudList {}, dtreeList {}, scardList {} ", antiFraudList, dtreeList, scardList);
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			initializedExtendList(event.getApplicationContext());
		}
	}

}
