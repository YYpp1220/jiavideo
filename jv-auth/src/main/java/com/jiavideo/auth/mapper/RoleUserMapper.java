package com.jiavideo.auth.mapper;

import com.jiavideo.auth.entity.RoleUser;
import com.jiavideo.auth.entity.RoleUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户角色映射器
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
public interface RoleUserMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(RoleUserExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(RoleUserExample example);

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
    int insert(RoleUser record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(RoleUser record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<RoleUser>}
     */
    List<RoleUser> selectByExample(RoleUserExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link RoleUser}
     */
    RoleUser selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") RoleUser record, @Param("example") RoleUserExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") RoleUser record, @Param("example") RoleUserExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(RoleUser record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(RoleUser record);
}