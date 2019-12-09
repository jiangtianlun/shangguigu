package service;

import bean.PmsProductSaleAttr;
import bean.PmsSkuInfo;

import java.util.List;

public interface PmsProductSaleAttrService {
    List<PmsProductSaleAttr> getByID(String spuId);
    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(PmsSkuInfo pmsSkuInfo);
}
