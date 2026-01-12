package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 向购物车新增数据接口
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看所有购物车信息
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * 删除购物车接口
     */
    void deleteShoppingCart();

}
