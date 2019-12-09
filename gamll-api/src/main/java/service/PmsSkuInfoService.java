package service;

import bean.PmsSkuInfo;

import java.util.List;

public interface PmsSkuInfoService {
    public void saveSku(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getItem(String skuid);

    List<PmsSkuInfo> getItems(String productId);

    List<PmsSkuInfo> getAll();
}
