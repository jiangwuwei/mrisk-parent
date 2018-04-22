package com.zoom.risk.platform.engine.mvel.impl;

import com.zoom.risk.platform.engine.mvel.MvelService;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by jiangyulin on 2015/12/6.
 */
@Service("mvelService")
public class MvelServiceImpl implements MvelService {

    public  boolean simpleEvaluate(Map<String, Object> mvelMap, CompiledExpression expression){
        return MVEL.executeExpression(expression, mvelMap, Boolean.class);
    }

    public  Float simpleFloatEvaluate(Map<String, Object> mvelMap, String expression){
        ExpressionCompiler compiler = new ExpressionCompiler(expression);
        CompiledExpression compilerExp = compiler.compile();
        return MVEL.executeExpression(compilerExp, mvelMap, Float.class);
    }
}
