package service;

import bean.PmsBaseAttrInfo;

import java.util.List;

public interface PmsBaseAttrInfoService {
    List<PmsBaseAttrInfo> getById(String catalog3Id);
}
