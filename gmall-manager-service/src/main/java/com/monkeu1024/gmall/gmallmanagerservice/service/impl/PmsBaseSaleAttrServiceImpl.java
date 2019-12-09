package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsBaseSaleAttr;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseSaleAttrDao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseSaleAttrService;

import java.util.List;

@Service
public class PmsBaseSaleAttrServiceImpl implements PmsBaseSaleAttrService {
    @Autowired
    PmsBaseSaleAttrDao pmsBaseSaleAttrDao;

    @Override
    public List<PmsBaseSaleAttr> selectAll() {
        return pmsBaseSaleAttrDao.selectAll();
    }
}
