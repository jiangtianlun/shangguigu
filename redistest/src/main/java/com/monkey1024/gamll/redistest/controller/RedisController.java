package com.monkey1024.gamll.redistest.controller;

import bean.PmsSkuInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.monkey1024.gamll.redistest.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import service.PmsSkuInfoService;

@Controller
public class RedisController {
    @Reference
    PmsSkuInfoService pmsSkuInfoService;

    @Autowired
    RedisUtil redisUtil;

    String sku_prefix="sku:";
    String sku_info=":info";
    @RequestMapping("redisTest/{skuid}")
    @ResponseBody
    public String redisTset(@PathVariable String skuid){
        Jedis jedis=redisUtil.getJedis();
        String infoJson=jedis.get(sku_prefix+skuid+sku_info);
        if(infoJson!=null){
            PmsSkuInfo pmsSkuInfo= JSON.parseObject(infoJson,PmsSkuInfo.class);
            System.out.println(pmsSkuInfo.getSkuName());
            jedis.close();
            return pmsSkuInfo.getSkuName();
        }else {
            PmsSkuInfo pmsSkuInfo=pmsSkuInfoService.getItem(skuid);
            jedis.set(sku_prefix+skuid+sku_info,JSON.toJSONString(pmsSkuInfo));
            return pmsSkuInfo.getId()+"放入缓存,Key为："+sku_prefix+skuid+sku_info;
        }

    }
}
