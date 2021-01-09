package com.jiavideo.auth.mapper;

import com.jiavideo.auth.entity.RoleResource;
import com.jiavideo.auth.entity.RoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 角色资源映射器
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
public interface RoleResourceMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(RoleResourceExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(RoleResourceExample example);

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
    int insert(RoleResource record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(RoleResource record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<RoleResource>}
     */
    List<RoleResource> selectByExample(RoleResourceExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link RoleResource}
     */
    RoleResource selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(RoleResource record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(RoleResource record);
}