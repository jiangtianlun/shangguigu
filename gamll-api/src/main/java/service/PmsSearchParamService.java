package service;

import bean.PmsSearchParam;
import bean.PmsSearchSkuInfo;

import java.io.IOException;
import java.util.List;

public interface PmsSearchParamService {

    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) throws IOException;
}
