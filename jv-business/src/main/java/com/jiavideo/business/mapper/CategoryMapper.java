package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.Category;
import com.jiavideo.business.entity.CategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 类别映射器
 *
 * @author MyMrDiao
 * @date 2020/10/18
 */
public interface CategoryMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(CategoryExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(CategoryExample example);

    /**
     * 按主键删除
     *
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入
     *
     * @param record 记录
     * @return int
     */
    int insert(Category record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Category record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Category>}
     */
    List<Category> selectByExample(CategoryExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Category}
     */
    Category selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Category record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Category record);
}