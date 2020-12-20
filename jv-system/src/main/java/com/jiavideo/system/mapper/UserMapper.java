package com.jiavideo.system.mapper;

import com.jiavideo.user.entity.User;
import com.jiavideo.user.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户映射器
 *
 * @author MyMrDiao
 * @date 2020/12/19
 */
public interface UserMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(UserExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(UserExample example);

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
    int insert(User record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(User record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<User>}
     */
    List<User> selectByExample(UserExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link User}
     */
    User selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(User record);
}