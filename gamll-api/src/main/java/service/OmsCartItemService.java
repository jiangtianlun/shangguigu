package service;

import bean.OmsCartItem;

import java.util.List;

public interface OmsCartItemService {
    List<OmsCartItem> getCart(String memberId);

    void addItem(OmsCartItem omsCartItem);

    OmsCartItem getItemByUser(String memberId, String skuId);

    void updateItem(OmsCartItem item);

    List<OmsCartItem> getCartByUser(String s);
}
