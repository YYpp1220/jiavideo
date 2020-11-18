package com.jiavideo.upload.mapper;

import com.jiavideo.file.entity.File;
import com.jiavideo.file.entity.FileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 文件映射器
 *
 * @author MyMrDiao
 * @date 2020/11/16
 */
public interface FileMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(FileExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(FileExample example);

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
    int insert(File record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(File record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<File>}
     */
    List<File> selectByExample(FileExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link File}
     */
    File selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") File record, @Param("example") FileExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") File record, @Param("example") FileExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(File record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(File record);
}