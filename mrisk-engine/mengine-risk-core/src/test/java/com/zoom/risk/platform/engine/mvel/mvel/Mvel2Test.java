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
public class Mvel2Test {

    @Test
    public void testStr1(){

        Map vars = new HashMap();
        vars.put("productType", "4");
        CompiledExpression exp = new ExpressionCompiler("productType == 4").compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void testStr2(){
        Map vars = new HashMap();
        vars.put("productType", "4");
        ExpressionCompiler compiler = new ExpressionCompiler("productType == '4' ");
        CompiledExpression exp = compiler.compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void testStr3(){
        Map vars = new HashMap();
        vars.put("Q0117_0000001", 4);
        vars.put("projectFlag", "0000000000000000000000000000000000000000010001000000000000000000");
        ExpressionCompiler compiler = new ExpressionCompiler(" (Q0117_0000001 > 1 && projectFlag == '0000000000000000000000000000000000000000010001000000000000000000') ");
        CompiledExpression exp = compiler.compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void contains(){
        Map vars = new HashMap();
        List<String> list = Arrays.asList("f12334","v146666","v146636","v146664");
        vars.put("Q0117_0000001", list);
        vars.put("riskId","f123345");
        ExpressionCompiler compiler = new ExpressionCompiler(" !Q0117_0000001.contains(riskId)  ");
        CompiledExpression exp = compiler.compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void sizeOf(){
        Map vars = new HashMap();
        List<String> list = Arrays.asList("f12334","v146666","v146636","v146664");
        vars.put("Q0117_0000001", list);
        vars.put("listLength",4);
        ExpressionCompiler compiler = new ExpressionCompiler(" Q0117_0000001.size() == listLength  ");
        CompiledExpression exp = compiler.compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void listContains(){
        Map vars = new HashMap();
        List<String> list = new ArrayList<>();
        vars.put("Q0114_0000010", list);
        vars.put("idNumber","wwwww");
        ExpressionCompiler compiler = new ExpressionCompiler(" (Q0114_0000010.size() > 3 || !Q0114_0000010.contains(idNumber))  ");
        CompiledExpression exp = compiler.compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void priority(){
        Map vars = new HashMap();
        List<String> list = new ArrayList<>();
        list.add("3333");
        vars.put("Q0114_0000010", list);
        //vars.put("idNumber","wwwww");
        ExpressionCompiler compiler = new ExpressionCompiler(" ( !Q0114_0000010.contains(idNumber) && Q0114_0000010.size() == 0 )  ");
        CompiledExpression exp = compiler.compile();
        Boolean result = MVEL.executeExpression(exp, vars, Boolean.class);
        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void nullsafe(){
        Map vars = new HashMap();
        List<String> list = new ArrayList<>();
        list.add("3333");
        vars.put("Q0114_0000010", list);
        vars.put("idNumber",null);
        ExpressionCompiler compiler = new ExpressionCompiler("Q0114_0000010.contains(idNumber)");
        CompiledExpression exp = compiler.compile();
        Object result = MVEL.executeExpression(exp, vars);
        System.out.print(result);
    }

    @Test
    public void containsABC(){
        Map vars = new HashMap();
        vars.put("Q0114_0000010","12.123.33.2");
        ExpressionCompiler compiler = new ExpressionCompiler("Q0114_0000010 contains ('12.123.33')");
        CompiledExpression exp = compiler.compile();
        Object result = MVEL.executeExpression(exp, vars);
        Assert.assertEquals(Boolean.TRUE, result);
        System.out.print(result);
    }

    @Test
    public void containsABCD(){
        Map vars = new HashMap();
        vars.put("Q0114_0000010","12.123.33.2");
        ExpressionCompiler compiler = new ExpressionCompiler("Q0114_0000010.contains ('12.123.33')");
        CompiledExpression exp = compiler.compile();
        Object result = MVEL.executeExpression(exp, vars);
        Assert.assertEquals(Boolean.TRUE, result);
        System.out.print(result);
    }

}
