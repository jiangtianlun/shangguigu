package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsSkuAttrValueDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsSkuImageDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsSkuInfoDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsSkuSaleAttrValueDao;
import com.monkeu1024.gmall.gmallmanagerservice.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import service.PmsSkuInfoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsSkuInfoServiceImpl implements PmsSkuInfoService {

    @Autowired
    PmsSkuAttrValueDao pmsSkuAttrValueDao;
    @Autowired
    PmsSkuImageDao pmsSkuImageDao;
    @Autowired
    PmsSkuSaleAttrValueDao pmsSkuSaleAttrValueDao;
    @Autowired
    PmsSkuInfoDao pmsSkuInfoDao;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSku(PmsSkuInfo pmsSkuInfo) {

        pmsSkuInfoDao.insertSelective(pmsSkuInfo);
       if(pmsSkuInfo.getSkuImageList()!=null){
        for(PmsSkuImage pmsSkuImage:pmsSkuInfo.getSkuImageList()){
            pmsSkuImage.setSkuId(pmsSkuInfo.getId());
            pmsSkuImageDao.insertSelective(pmsSkuImage);
        }
       }
       if(pmsSkuInfo.getSkuAttrValueList()!=null){
           for(PmsSkuSaleAttrValue pmsSkuSaleAttrValue:pmsSkuInfo.getSkuSaleAttrValueList()){
               pmsSkuSaleAttrValueDao.insertSelective(pmsSkuSaleAttrValue);
           }
       }
       if (pmsSkuInfo.getSkuAttrValueList()!=null){
           for (PmsSkuAttrValue pmsSkuAttrValue:pmsSkuInfo.getSkuAttrValueList()){
               pmsSkuAttrValueDao.insertSelective(pmsSkuAttrValue);
           }
       }
    }

    @Override
    public PmsSkuInfo getItem(String skuid) {
        Jedis jedis = redisUtil.getJedis();
        String name="gmall:"+skuid+":item";
        if(jedis.get(name)!=null){
            return JSON.parseObject(jedis.get(name),PmsSkuInfo.class);
        }
        PmsSkuInfo pmsSkuInfo=new PmsSkuInfo();
        pmsSkuInfo.setId(skuid);
        pmsSkuInfo =pmsSkuInfoDao.selectOne(pmsSkuInfo);
        PmsSkuImage pmsSkuImage=new PmsSkuImage();
        pmsSkuImage.setSkuId(skuid);
        pmsSkuInfo.setSkuImageList(pmsSkuImageDao.select(pmsSkuImage));
        jedis.set(name,JSON.toJSONString(pmsSkuInfo));
        return  pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getItems(String productId) {
        Jedis jedis = redisUtil.getJedis();
        PmsSkuInfo pmsSkuInfo=new PmsSkuInfo();
        pmsSkuInfo.setProductId(productId);
        List<PmsSkuInfo> list=null;
        String name="gmall:"+productId+":items";
        if(jedis.llen(name)!=0){
            list =new ArrayList<PmsSkuInfo>();
           while(jedis.llen(name)!=0){
              String item= jedis.lpop(name);
              list.add(JSON.parseObject(item,PmsSkuInfo.class));
           }
           System.out.println("缓存走一遭");
            return list;
        }else {
            list = pmsSkuInfoDao.select(pmsSkuInfo);
            for (PmsSkuInfo skuInfo : list) {
                PmsSkuSaleAttrValue saleAttrValue = new PmsSkuSaleAttrValue();
                saleAttrValue.setSkuId(skuInfo.getId());
                skuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValueDao.select(saleAttrValue));
                jedis.lpush(name, JSON.toJSONString(skuInfo));
            }
            jedis.expire(name,60*60);
            return list;
        }
    }

    @Override
    public List<PmsSkuInfo> getAll() {
        List<PmsSkuInfo> list=pmsSkuInfoDao.selectAll();
        for(PmsSkuInfo pmsSkuInfo:list)
        {
            PmsSkuAttrValue pmsSkuAttrValue=new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValueDao.select(pmsSkuAttrValue));
        }
        System.out.println(list==null);
        return list;

    }

}
