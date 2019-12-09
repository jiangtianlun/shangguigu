package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsBaseAttrInfo;
import bean.PmsBaseAttrValue;
import bean.PmsProductSaleAttrValue;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseAttrInfoDao;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsBaseAttrValueDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsBaseAttrInfoService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Set;

@Service
public class PmsBaseAttrInfoServiceImpl implements PmsBaseAttrInfoService {

    @Autowired
    PmsBaseAttrInfoDao pmsBaseAttrInfoDao;

    @Autowired
    PmsBaseAttrValueDao pmsBaseAttrValueDao;

    @Override
    public List<PmsBaseAttrInfo> getById(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo=new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> list=pmsBaseAttrInfoDao.select(pmsBaseAttrInfo);
        for(PmsBaseAttrInfo baseAttrInfo:list)
        {
            PmsBaseAttrValue pmsBaseAttrValue=new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            baseAttrInfo.setAttrValueList(pmsBaseAttrValueDao.select(pmsBaseAttrValue));
        }
        return list;
    }

    @Override
    public boolean savePmsBaseAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        if(StringUtil.isEmpty(pmsBaseAttrInfo.getId()))
        {
            pmsBaseAttrInfoDao.insertSelective(pmsBaseAttrInfo);
        }
            if(!StringUtil.isEmpty(pmsBaseAttrInfo.getId()))
            {
                Example example=new Example(PmsBaseAttrValue.class);
                example.createCriteria().andEqualTo("attrId",pmsBaseAttrInfo.getId());
                pmsBaseAttrValueDao.deleteByExample(example);
            }
                for (PmsBaseAttrValue p : pmsBaseAttrInfo.getAttrValueList()) {
                    p.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueDao.insertSelective(p);
                }


        return true;
    }

    @Override
    public List<PmsBaseAttrInfo> getBaseAttrInfosByAttrId(Set<String> attrset) {
        List<PmsBaseAttrInfo> list = null;
        String attrIds=null;
        attrIds=StringUtils.join(attrset,",");
        list=pmsBaseAttrInfoDao.getBaseAttrInfosByAttrId(attrIds);
        return list;
    }
}
