package com.monkey1024.gmall.gmallcartweb.controller;

import bean.OmsCartItem;
import bean.PmsSkuInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.monkey1024.gmall.gmallcartweb.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.OmsCartItemService;
import service.PmsSkuInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Reference
    PmsSkuInfoService pmsSkuInfoService;

    @Reference
    OmsCartItemService omsCartItemService;
    @RequestMapping("checkCart")
    public String checkCart(Model model,String isChecked,String skuId){
        System.out.println("checkCart");
        List<OmsCartItem> list=omsCartItemService.getCartByUser("1");
        for (OmsCartItem omsCartItem : list) {
            if (omsCartItem.getProductSkuId().equals(skuId)){
                omsCartItem.setIsChecked(isChecked);
                omsCartItemService.updateItem(omsCartItem);
            }
        }
        model.addAttribute("cartList",list);


        return "cartListInner";
    }

    @RequestMapping("cartList")
    public ModelAndView carList(ModelAndView mv){
        List<OmsCartItem> list=omsCartItemService.getCartByUser("1");
        mv.addObject("cartList",list);
        mv.setViewName("cartList");
        return  mv;
    }

    @RequestMapping("addToCart")
    public String addToCart(String num, String skuId, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {
        String memberId = "1";
        PmsSkuInfo skuInfo = pmsSkuInfoService.getItem(skuId);
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setModifyDate(new Date());
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductAttr("");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("11111111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(Integer.parseInt(num));
        List<OmsCartItem> cart = null;
        if (StringUtils.isNotBlank(memberId)) {
            //用户登录使用的购物车
            OmsCartItem item=omsCartItemService.getItemByUser(memberId,skuId);
            if (item==null){
                omsCartItem.setMemberId(memberId);
                omsCartItemService.addItem(omsCartItem);
            }else{
                item.setQuantity(item.getQuantity()+omsCartItem.getQuantity());
                omsCartItemService.updateItem(item);
            }

        } else {
            //用户未登录使用的购物车
            String cookieCartValue = CookieUtil.getCookieValue(request, "cookieCart", true);
            if (cookieCartValue == null) {
                cart = new ArrayList<OmsCartItem>();
                cart.add(omsCartItem);
            } else {
                cart = JSON.parseArray(cookieCartValue, OmsCartItem.class);
                System.out.println("使用了Cookie："+cookieCartValue);
                if (skuIsExist(cart, omsCartItem)) {

                } else {
                    cart.add(omsCartItem);
                }
            }
            CookieUtil.setCookie(request,response,"cookieCart",JSON.toJSONString(cart),60*60*24,true);
        }
        System.out.println(JSON.toJSONString(cart));
        return "redirect:/success.html";
    }

    private boolean skuIsExist(List<OmsCartItem> cart, OmsCartItem omsCartItem) {
        for (OmsCartItem cartItem : cart) {
            if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                cartItem.setQuantity(cartItem.getQuantity() + omsCartItem.getQuantity());
                return true;
            }
        }
        return false;
    }
}
