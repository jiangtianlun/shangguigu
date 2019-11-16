package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsBaseAttrValue;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseAttrValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseAttrValueService;

import java.util.List;

@Service
public class PmsBaseAttrValueServiceImpl implements PmsBaseAttrValueService{
    @Autowired
    PmsBaseAttrValueDao pmsBaseAttrValueDao;

    @Override
    public List<PmsBaseAttrValue> getById(String attrId) {
        return null;
    }
}
