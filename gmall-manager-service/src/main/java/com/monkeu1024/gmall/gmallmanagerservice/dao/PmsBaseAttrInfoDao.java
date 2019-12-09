package com.monkeu1024.gmall.gmallmanagerservice.dao;

import bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PmsBaseAttrInfoDao extends Mapper<PmsBaseAttrInfo>{
    List<PmsBaseAttrInfo> getBaseAttrInfosByAttrId(@Param("attrIds") String attrIds);
}
