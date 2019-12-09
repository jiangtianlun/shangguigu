package service;

import bean.PmsProductInfo;
import bean.PmsSkuInfo;

import java.util.List;

public interface PmsProductInfoService {
   public List<PmsProductInfo> getByID(String catalog3Id);
   public void savePmsProductInfo(PmsProductInfo pmsProductInfo);


}
