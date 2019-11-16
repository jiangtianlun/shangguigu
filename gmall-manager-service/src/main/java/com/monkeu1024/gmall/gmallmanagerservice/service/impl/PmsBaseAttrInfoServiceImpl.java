package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsBaseAttrInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseAttrInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseAttrInfoService;

import java.util.List;

@Service
public class PmsBaseAttrInfoServiceImpl implements PmsBaseAttrInfoService {

    @Autowired
    PmsBaseAttrInfoDao pmsBaseAttrInfoDao;

    @Override
    public List<PmsBaseAttrInfo> getById(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo=new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        return pmsBaseAttrInfoDao.select(pmsBaseAttrInfo);
    }
}
