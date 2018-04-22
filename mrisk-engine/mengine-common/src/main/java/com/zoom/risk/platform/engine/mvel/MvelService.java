package com.zoom.risk.platform.engine.mvel;

import org.mvel2.compiler.CompiledExpression;

import java.util.Map;

/**
 * Created by jiangyulin on 2015/12/6.
 */
public interface MvelService {

    boolean simpleEvaluate(Map<String, Object> mvelMap, CompiledExpression expression);

    Float simpleFloatEvaluate(Map<String, Object> mvelMap, String expression);

}
