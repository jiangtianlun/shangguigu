package com.monkeu1024.gmall.gmallmanagerservice.service.impl;

import bean.PmsProductImage;
import com.alibaba.dubbo.config.annotation.Service;
import com.monkeu1024.gmall.gmallmanagerservice.dao.PmsProductImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsProductImageService;

import java.util.List;

@Service
public class PmsProductImageServiceImpl implements PmsProductImageService {

    @Autowired
    PmsProductImageDao pmsProductImageDao;
    @Override
    public List<PmsProductImage> getByID(String spuId) {
        PmsProductImage pmsProductImage=new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        return pmsProductImageDao.select(pmsProductImage);
    }
}
