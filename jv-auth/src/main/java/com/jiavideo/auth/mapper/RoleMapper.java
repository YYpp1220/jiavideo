package com.jiavideo.auth.mapper;

import com.jiavideo.auth.entity.Role;
import com.jiavideo.auth.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 角色映射器
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
public interface RoleMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(RoleExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(RoleExample example);

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
    int insert(Role record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Role record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Role>}
     */
    List<Role> selectByExample(RoleExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Role}
     */
    Role selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Role record);
}