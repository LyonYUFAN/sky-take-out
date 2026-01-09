package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FlavorMapper {

    /**
     * 根据菜品ID查找关联口味
     * @param dishId
     * @return
     */
    @Select("select * from `dish_flavor` where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);

    /**
     * 根据菜品Id删除关联口味
     * @param dishId
     */
    @Delete("delete from `dish_flavor` where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    void insertBatch(List<DishFlavor> flavors);

}
