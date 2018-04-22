package decision.vo;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.operating.decision.vo.Route;
import com.zoom.risk.operating.decision.vo.RouteMode;
import org.junit.Test;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public class RouteModeTest {
    @Test
    public void roterModeltest(){
        String name= "{\"isJoin\":true,\"routes\":[{\"paramName\":\"creditScore\",\"operation\":\">\",\"value\":\"300\",\"paramDataType\":1},{\"paramName\":\"creditScore\",\"operation\":\"<=\",\"value\":\"500\",\"paramDataType\":1}]}";
        RouteMode model = JSON.parseObject(name,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }


    @Test
    public void roterModeltest2(){
         RouteMode m = RouteMode.andRoute(new Route("芝麻信用分","creditScore", Route.PARAM_DATA_TYPE_DIGITAL, Route.GET, "300","大于等于300"),  new Route("芝麻信用分","creditScore", Route.PARAM_DATA_TYPE_DIGITAL, Route.LT, "500","小于500"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }

    @Test
    public void roterModeltest3(){
        RouteMode m = new RouteMode(new Route("芝麻信用分","creditScore", Route.PARAM_DATA_TYPE_DIGITAL, Route.GET, "500","大于等于500"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }

    @Test
    public void roterModeltest4(){
        RouteMode m = new RouteMode(new Route("芝麻信用分","creditScore", Route.PARAM_DATA_TYPE_DIGITAL, Route.LT, "300","小于300"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }

    @Test
    public void roterModeltest5(){
        RouteMode m = new RouteMode(new Route("额度贷逾期还款","cashOverdue", Route.PARAM_DATA_TYPE_DIGITAL, Route.EQ, "1","有逾期还款"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }

    @Test
    public void roterModeltest6(){
        RouteMode m = new RouteMode(new Route("额度贷逾期还款","cashOverdue", Route.PARAM_DATA_TYPE_DIGITAL, Route.EQ, "0","无逾期还款"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }

    @Test
    public void roterModeltest7(){
        RouteMode m = new RouteMode(new Route("手机实名","realName", Route.PARAM_DATA_TYPE_DIGITAL, Route.EQ, "1","已实名认证"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }

    @Test
    public void roterModeltest8(){
        RouteMode m = new RouteMode(new Route("手机实名","realName", Route.PARAM_DATA_TYPE_DIGITAL, Route.EQ, "0","未实名认证"));
        String dd = JSON.toJSONString(m);
        System.out.println(dd);
        RouteMode model = JSON.parseObject(dd,new TypeToken<RouteMode>(){}.getType());
        System.out.println(model.getMevlExpression());
    }
}
