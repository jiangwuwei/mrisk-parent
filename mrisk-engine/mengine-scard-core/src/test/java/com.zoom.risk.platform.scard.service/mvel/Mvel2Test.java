package com.zoom.risk.platform.scard.service.mvel;

import org.junit.Assert;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiangyulin on 2017/3/13.
 */
public class Mvel2Test {

    @Test
    public void containsABCD(){
        Map vars = new HashMap();
        vars.put("scAage",34);
        ExpressionCompiler compiler = new ExpressionCompiler(" 20 < scAage && scAage <= 32");
        CompiledExpression exp = compiler.compile();
        Object result = MVEL.executeExpression(exp, vars);
        Assert.assertEquals(Boolean.TRUE, result);
        System.out.print(result);
    }

    @Test
    public void tst() {
        Pattern pattern = Pattern.compile("(^\\$)|(\\$$)");
        Matcher matcher = pattern.matcher(" 30 < $");
        System.out.println(matcher.find());

    }

}
