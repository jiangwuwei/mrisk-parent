package com.zoom.risk.platform.engine.mvel.mvel;

import org.junit.Assert;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

import java.util.*;

/**
 * Created by jiangyulin on 2017/3/13.
 */
public class MvelMutipluTest {

    @Test
    public void testStr1(){
        Map vars = new HashMap();
        Map<String,Object> values = new HashMap<>();
        values.put("courtDiscreditType","12");
        values.put("courtDiscreditStatus","12");
        values.put("courtExecutionType","12");
        values.put("courtExecutionStatus","12");
        values.put("personZT","-1");
        values.put("personWF","1");
        values.put("personSD","-1");
        values.put("personXD","1");
        vars.put("Q0117_0000001", values);
        CompiledExpression exp = new ExpressionCompiler("Q0117_0000001.personXD == '1'").compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }
}
