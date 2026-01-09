package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 根据套餐ID查询对应菜品接口
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 修改菜品的基本信息以及口味接口
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 回显菜品修改页面接口
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 保存菜品与口味接口
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 分页查询菜品功能接口
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品口味接口
     * @param ids
     */
    void deleteBatch(List<Long> ids);

}
