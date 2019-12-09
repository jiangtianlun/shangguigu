package com.monkey1024.gmall.gmallcartservice.serviceimpl;

import bean.OmsCartItem;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.monkey1024.gmall.gmallcartservice.dao.OmsCartItemDao;
import com.monkey1024.gmall.gmallcartservice.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import service.OmsCartItemService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OmsCartItemServiceImpl implements OmsCartItemService{
    @Autowired
    OmsCartItemDao omsCartItemDao;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<OmsCartItem> getCart(String memberId) {
       String name="gmall:"+memberId+":cart";
        Jedis jedis = redisUtil.getJedis();
        String cart = jedis.get(name);
        List<OmsCartItem> cartItems;
        if(cart==null)
        {
            OmsCartItem omsCartItem=new OmsCartItem();
            omsCartItem.setMemberId(memberId);
            cartItems=omsCartItemDao.select(omsCartItem);
        }else
        {
            cartItems= JSON.parseArray(cart,OmsCartItem.class);
        }

        return  cartItems;
    }

    @Override
    public void addItem(OmsCartItem omsCartItem) {
        omsCartItemDao.insertSelective(omsCartItem);
    }

    @Override
    public OmsCartItem getItemByUser(String memberId, String skuId) {
        List<OmsCartItem> cart = getCart(memberId);
        for (OmsCartItem item : cart) {
            if (item.getProductSkuId().equals(skuId)){
                return item;
            }
        }
        return null;
    }

    @Override
    public void updateItem(OmsCartItem item) {
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo("memberId",item.getMemberId()).andEqualTo("productSkuId",item.getProductSkuId());
        omsCartItemDao.updateByExample(item,example);
    }

    @Override
    public List<OmsCartItem> getCartByUser(String s) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(s);
        List<OmsCartItem> cartItems=omsCartItemDao.select(omsCartItem);
        return cartItems;
    }
}
