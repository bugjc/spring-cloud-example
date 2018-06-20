package com.bugjc.zuul.web;
import com.bugjc.zuul.core.dto.Result;
import com.bugjc.zuul.core.dto.ResultGenerator;
import com.bugjc.zuul.event.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 路由信息控制器
 */
@RestController
public class RouteController {

    @Autowired
    RefreshRouteService refreshRouteService;

    @RequestMapping("/refreshRoute")
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/watchNowRoute")
    public Result watchNowRoute(){
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return ResultGenerator.genSuccessResult(handlerMap);
    }



}
