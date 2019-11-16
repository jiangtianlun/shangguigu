package service;

import bean.PmsBaseAttrValue;

import java.util.List;

public interface PmsBaseAttrValueService {
    List<PmsBaseAttrValue> getById(String attrId);
}
