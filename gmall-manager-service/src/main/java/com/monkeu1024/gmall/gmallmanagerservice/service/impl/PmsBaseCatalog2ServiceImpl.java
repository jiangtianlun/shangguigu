package com.monkeu1024.gmall.gmallmanagerservice.service.impl;


import bean.PmsBaseCatalog2;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseCatalog2Dao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseCatalog2Service;

import java.util.List;

@Service
public class PmsBaseCatalog2ServiceImpl implements PmsBaseCatalog2Service{

    @Autowired
    PmsBaseCatalog2Dao pmsBaseCatalog2Dao;
    @Override
    public List<PmsBaseCatalog2> getByID(String ID) {
        PmsBaseCatalog2 pmsBaseCatalog2=new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(ID);
        return pmsBaseCatalog2Dao.select(pmsBaseCatalog2);
    }
}
