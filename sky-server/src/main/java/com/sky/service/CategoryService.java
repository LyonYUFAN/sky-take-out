package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    /**
     * 新增菜品分类接口
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分类分页接口
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult query(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 按照分类类型查询
     * @param type
     * @return
     */
    List<Category> list(Integer type);

    /**
     * 修改分类接口
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用禁用分类接口
     * @param status
     * @param id
     */
    void StartOrStop(Integer status, Long id);

    /**
     * 删除分类接口
     * @param id
     */
    void deleteById(Long id);
}
