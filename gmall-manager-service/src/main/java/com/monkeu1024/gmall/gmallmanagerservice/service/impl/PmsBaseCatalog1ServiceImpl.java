package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsBaseCatalog1;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseCatalog1Dao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseCatalog1Service;

import java.util.List;

@Service
public class PmsBaseCatalog1ServiceImpl implements PmsBaseCatalog1Service {

    @Autowired
    PmsBaseCatalog1Dao pmsBaseCatalog1Dao;

    @Override
    public List<PmsBaseCatalog1> getAll() {

        return pmsBaseCatalog1Dao.selectAll();
    }
}
