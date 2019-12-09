package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsProductSaleAttr;
import bean.PmsProductSaleAttrValue;
import bean.PmsSkuInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductSaleAttrDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductSaleAttrValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsProductSaleAttrService;

import java.util.List;

@Service
public class PmsProductSaleAttrServiceImpl implements PmsProductSaleAttrService {

    @Autowired
    PmsProductSaleAttrDao pmsProductSaleAttrDao;

    @Autowired
    PmsProductSaleAttrValueDao pmsProductSaleAttrValueDao;

    @Override
    public List<PmsProductSaleAttr> getByID(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr=new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> list=pmsProductSaleAttrDao.select(pmsProductSaleAttr);
        for(PmsProductSaleAttr productSaleAttr:list){
            PmsProductSaleAttrValue productSaleAttrValue=new PmsProductSaleAttrValue();
            productSaleAttrValue.setProductId(productSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> l=pmsProductSaleAttrValueDao.select(productSaleAttrValue);
            productSaleAttr.setSpuSaleAttrValueList(l);
        }
        return list;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(PmsSkuInfo pmsSkuInfo) {
        String productId =pmsSkuInfo.getProductId();
        String skuId =pmsSkuInfo.getId();
        return pmsProductSaleAttrDao.spuSaleAttrListCheckBySku(productId,skuId);
    }
}
