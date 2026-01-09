package com.sky.controller.admin;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    private RedisTemplate  redisTemplate;

    /**
     * 设置店铺的打样/营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    public Result setShopStatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }

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

