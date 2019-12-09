package service;

import bean.PmsBaseAttrInfo;

import java.util.List;
import java.util.Set;

public interface PmsBaseAttrInfoService {
    List<PmsBaseAttrInfo> getById(String catalog3Id);
    boolean savePmsBaseAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrInfo> getBaseAttrInfosByAttrId(Set<String> attrset);
}
