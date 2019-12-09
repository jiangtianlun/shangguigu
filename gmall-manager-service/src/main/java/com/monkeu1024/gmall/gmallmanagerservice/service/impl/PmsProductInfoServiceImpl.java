package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductImageDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductInfoDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductSaleAttrDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductSaleAttrValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsProductInfoService;

import java.util.List;

@Service
public class PmsProductInfoServiceImpl implements PmsProductInfoService{

    @Autowired
    PmsProductInfoDao pmsProductInfoDao;
    @Autowired
    PmsProductImageDao pmsProductImageDao;

    @Autowired
    PmsProductSaleAttrDao pmsProductSaleAttrDao;

    @Autowired
    PmsProductSaleAttrValueDao pmsProductSaleAttrValueDao;



    @Override
    public List<PmsProductInfo> getByID(String catalog3Id) {
        PmsProductInfo pmsProductInfo=new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return pmsProductInfoDao.select(pmsProductInfo);
    }

    @Override
    public void savePmsProductInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfoDao.insertSelective(pmsProductInfo);
        for (PmsProductImage image:pmsProductInfo.getSpuImageList()){
            image.setProductId(pmsProductInfo.getId());
            pmsProductImageDao.insertSelective(image);
        }
        for (PmsProductSaleAttr saleAttr:pmsProductInfo.getSpuSaleAttrList()){
            saleAttr.setProductId(pmsProductInfo.getId());
            pmsProductSaleAttrDao.insertSelective(saleAttr);
            for (PmsProductSaleAttrValue value:saleAttr.getSpuSaleAttrValueList()){
                value.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrValueDao.insertSelective(value);
            }


        }
    }


}
