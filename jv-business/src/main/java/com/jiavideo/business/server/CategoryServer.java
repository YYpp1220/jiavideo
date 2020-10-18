package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.CategoryDTO;
import com.jiavideo.business.entity.Category;
import com.jiavideo.business.entity.CategoryExample;
import com.jiavideo.business.mapper.CategoryMapper;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 目录服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServer {
    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    /**
     * 查询所有
     * @return {@link List<CategoryDTO>}
     */
    public PageResult<CategoryDTO> queryAll() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(example);
        List<CategoryDTO> categoryDTOList = categoryList.stream().map(category -> JSONUtil.toBean(JSONUtil.toJsonStr(category), CategoryDTO.class)).collect(Collectors.toList());
        /*PageResult<CategoryDTO> categoryDTOPageResult = new PageResult<>(0, 0, categoryDTOList);*/
        PageResult<CategoryDTO> categoryDTOPageResult = new PageResult<>();
        categoryDTOPageResult.setGeneralClass(categoryDTOList);
        return categoryDTOPageResult;
    }

    /**
     * 查询所有
     * @return {@link List<CategoryDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<CategoryDTO> queryAllVice(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(example);
        List<CategoryDTO> categoryDTOList = categoryList.stream().map(category -> JSONUtil.toBean(JSONUtil.toJsonStr(category), CategoryDTO.class)).collect(Collectors.toList());
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categoryList);
        PageResult<CategoryDTO> categoryDTOPageResult = new PageResult<>(categoryPageInfo.getTotal(), categoryPageInfo.getPages(), categoryDTOList);
        return categoryDTOPageResult;
    }

    /**
     * 保存
     * @param categoryDTO 章dto
     */
    public void save(CategoryDTO categoryDTO){
        Category category = JSONUtil.toBean(JSONUtil.toJsonStr(categoryDTO), Category.class);
        if (StringUtils.isEmpty(categoryDTO.getId())) {
            this.insert(category);
        }else {
            this.update(category);
        }
    }

    /**
     * 更新
     *
     * @param category 章
     */
    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 插入
     *
     * @param category 章
     */
    private void insert(Category category){
        category.setId(UUIDUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    /**
     * 删除通过id
     *
     * @param categoryId 章id
     */
    public void deleteById(String categoryId) {
        deleteChildren(categoryId);
        categoryMapper.deleteByPrimaryKey(categoryId);
    }

    /**
     * 删除的孩子
     *
     * @param categoryId 类别id
     */
    private void deleteChildren(String categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if ("00000000".equals(category.getParent())) {
            CategoryExample categoryExample = new CategoryExample();
            CategoryExample.Criteria criteria = categoryExample.createCriteria();
            criteria.andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(categoryExample);
        }
    }
}