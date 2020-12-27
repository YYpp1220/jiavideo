package com.jiavideo.auth.mapper;

import com.jiavideo.auth.entity.Resource;
import com.jiavideo.auth.entity.ResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 资源映射器
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
public interface ResourceMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(ResourceExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(ResourceExample example);

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
    int insert(Resource record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Resource record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Resource>}
     */
    List<Resource> selectByExample(ResourceExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Resource}
     */
    Resource selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Resource record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Resource record);
}