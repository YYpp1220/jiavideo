package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.Section;
import com.jiavideo.business.entity.SectionExample;
import java.util.List;

/**
 * 部分映射器
 *
 * @author MyMrDiao
 * @date 2020/10/06
 */
public interface SectionMapper {
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
    int insert(Section record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Section record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Section>}
     */
    List<Section> selectByExample(SectionExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Section}
     */
    Section selectByPrimaryKey(String id);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Section record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Section record);
}