package com.sky.controller.user;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    private RedisTemplate  redisTemplate;

    /**
     * 查询店铺状态
     * @return
     */
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer shop_status = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(shop_status);
    }
}

