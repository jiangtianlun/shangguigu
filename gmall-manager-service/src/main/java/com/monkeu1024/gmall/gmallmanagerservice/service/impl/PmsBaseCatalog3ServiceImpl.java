package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsBaseCatalog3;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseCatalog3Dao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseCatalog3Service;

import java.util.List;

@Service
public class PmsBaseCatalog3ServiceImpl implements PmsBaseCatalog3Service{
    @Autowired
    PmsBaseCatalog3Dao pmsBaseCatalog3Dao;

    @Override
    public List<PmsBaseCatalog3> getById(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3=new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        return pmsBaseCatalog3Dao.select(pmsBaseCatalog3);
    }
}
