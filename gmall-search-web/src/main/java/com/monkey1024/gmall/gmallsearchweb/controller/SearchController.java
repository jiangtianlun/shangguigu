package com.monkey1024.gmall.gmallsearchweb.controller;

import bean.*;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.PmsBaseAttrInfoService;
import service.PmsSearchParamService;

import java.io.IOException;
import java.util.*;

@Controller
public class SearchController {
    @Reference
    PmsSearchParamService pmsSearchParamService;

    @Reference
    PmsBaseAttrInfoService pmsBaseAttrInfoService;

    @RequestMapping("/list.html")
    public ModelAndView listHtml(PmsSearchParam pmsSearchParam, ModelAndView mv) throws IOException {
        if (pmsSearchParam.getKeyword() != null || pmsSearchParam.getCatalog3Id() != null || pmsSearchParam.getKeyword() != null) {
            List<PmsSearchSkuInfo> list = pmsSearchParamService.list(pmsSearchParam);
            if (list != null) {
                mv.addObject("skuLsInfoList", list);
                mv.setViewName("list");
            } else {
                mv.setViewName("index");
            }

            Set<String> attrset = new HashSet<String>();
            for (PmsSearchSkuInfo pmsSearchSkuInfo : list) {
                for (PmsSkuAttrValue pmsSkuAttrValue : pmsSearchSkuInfo.getSkuAttrValueList()) {
                    attrset.add(pmsSkuAttrValue.getValueId());
                }
            }
            List<PmsBaseAttrInfo> baseAttrInfos = pmsBaseAttrInfoService.getBaseAttrInfosByAttrId(attrset);
            String[] delvalueIds = pmsSearchParam.getValueId();
            List<PmsSearchCrumb> crumbs=new ArrayList<PmsSearchCrumb>();
            String urlParam = getUrlParam(pmsSearchParam);
            System.out.println(urlParam);
            mv.addObject("urlParam", urlParam);
            //删除已选中的属性生成面包屑
            if (delvalueIds != null) {
                Iterator<PmsBaseAttrInfo> iterator = baseAttrInfos.iterator();
                while (iterator.hasNext()) {
                    PmsBaseAttrInfo next = iterator.next();
                    for (PmsBaseAttrValue pmsBaseAttrValue : next.getAttrValueList()) {
                        for (String delvalueId : delvalueIds) {
                            if (delvalueId.equals(pmsBaseAttrValue.getId())) {
                                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                                pmsSearchCrumb.setValueId(delvalueId);
                                pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                                pmsSearchCrumb.setUrlParam(getUrlParamWithOutDelId(pmsSearchParam,delvalueId));
                                crumbs.add(pmsSearchCrumb);
                                iterator.remove();
                            }
                        }
                    }
                }
//                for(String d:delvalueIds) {
//                    PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
//                    pmsSearchCrumb.setValueId(d);
//                    pmsSearchCrumb.setValueName(d);
//                    pmsSearchCrumb.setUrlParam(getUrlParamWithOutDelId(pmsSearchParam,d));
//                    crumbs.add(pmsSearchCrumb);
//                }
            }
            mv.addObject("attrList", baseAttrInfos);

            mv.addObject("attrValueSelectedList",crumbs);

            return mv;
        } else {
            mv.setViewName("index");
            return mv;
        }
    }

    private String getUrlParamWithOutDelId(PmsSearchParam pmsSearchParam, String d) {
        String urlParam = "";
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueIds = pmsSearchParam.getValueId();
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        if (valueIds != null) {

            for (String pmsSkuAttrValue : valueIds) {
                if(pmsSkuAttrValue!=d) {
                    urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
                }
            }
        }


        return urlParam;
    }


    private String getUrlParam(PmsSearchParam pmsSearchParam) {
        String urlParam = "";
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueIds = pmsSearchParam.getValueId();
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        if (valueIds != null) {

            for (String pmsSkuAttrValue : valueIds) {
                urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
            }
        }


        return urlParam;
    }

}
