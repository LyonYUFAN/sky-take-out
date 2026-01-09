package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private FlavorMapper flavorMapper;
    @Autowired
    private SetmealDishMapper  setmealDishMapper;

    /**
     * 根据套餐ID查询对应菜品接口实现
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    /**
     * 修改菜品的基本信息以及口味接口实现
     * @param dishDTO
     */
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //修改菜品基本信息
        dishMapper.update(dish);
        //先删除掉口味信息
        flavorMapper.deleteByDishId(dish.getId());
        //添加口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            for(DishFlavor flavor : flavors){
                flavor.setDishId(dishDTO.getId());
            }
        }
        flavorMapper.insertBatch(flavors);
    }

    /**
     * 回显菜品修改信息接口实现
     * @param id
     * @return
     */
    public DishVO getById(Long id) {
        //拿到菜品信息
        Dish dish = dishMapper.getById(id);

        //拿到口味信息
        List<DishFlavor> flavors = flavorMapper.getByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /**
     * 批量删除菜品口味接口实现
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //如果状态是在售就不能删
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //如果关联了其他的套餐就不能删
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds !=null && setmealIds.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long dishId : ids) {
            //删除dish表中的数据
            dishMapper.deleteById(dishId);
            //删除flavor表中的数据
            flavorMapper.deleteByDishId(dishId);
        }
    }

    /**
     * 分页查询菜品功能接口
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 保存菜品与口味接口实现
     * @param dishDTO
     */
    @Transactional //事务处理
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //插入一条到dish表
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        //插入n条到flavor表
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            for(DishFlavor flavor : flavors){
                flavor.setDishId(dishId);
            }
        }
        flavorMapper.insertBatch(flavors);
    }
}

