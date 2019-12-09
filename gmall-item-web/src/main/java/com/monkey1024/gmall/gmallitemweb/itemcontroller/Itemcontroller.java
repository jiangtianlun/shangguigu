package com.monkey1024.gmall.gmallitemweb.itemcontroller;


import bean.PmsProductInfo;
import bean.PmsProductSaleAttr;
import bean.PmsSkuInfo;
import bean.PmsSkuSaleAttrValue;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.PmsProductInfoService;
import service.PmsProductSaleAttrService;
import service.PmsSkuInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class Itemcontroller {
    @Reference
    PmsProductInfoService pmsProductInfoService;

    @Reference
    PmsSkuInfoService pmsSkuInfoService;

    @Reference
    PmsProductSaleAttrService pmsProductSaleAttrService;
    @RequestMapping("/item/{skuid}")
    public ModelAndView getItem(ModelAndView mv, @PathVariable String skuid) {

        Map<String,String> skuhash=new HashMap<String, String>();
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoService.getItem(skuid);
        mv.addObject("skuInfo", pmsSkuInfo);
        mv.setViewName("item");
        List<PmsProductSaleAttr> spuSaleAttrList=pmsProductSaleAttrService.spuSaleAttrListCheckBySku(pmsSkuInfo);
        List<PmsSkuInfo> PmsSkuInfoList=pmsSkuInfoService.getItems(pmsSkuInfo.getProductId());
        for(PmsSkuInfo sku:PmsSkuInfoList){
            if (sku.getSkuSaleAttrValueList().size()!=0) {
                StringBuilder id = new StringBuilder();
                for (PmsSkuSaleAttrValue attrValue : sku.getSkuSaleAttrValueList()) {
                    id.append(attrValue.getSaleAttrValueId());
                    id.append("|");
                }
                skuhash.put(id.toString(), sku.getId());

            }
        }
        String map= JSON.toJSONString(skuhash);
        System.out.println(map);
        mv.addObject("spuSaleAttrListCheckBySku",spuSaleAttrList);
        mv.addObject("skuhash",map);
        return mv;
    }

}
