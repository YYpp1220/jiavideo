package com.jiavideo.auth.server;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.auth.dto.ResourceDTO;
import com.jiavideo.auth.entity.Resource;
import com.jiavideo.auth.entity.ResourceExample;
import com.jiavideo.auth.mapper.ResourceMapper;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 章服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServer {
    @Autowired(required = false)
    private ResourceMapper resourceMapper;

    /**
     * 查询所有
     * @return {@link List<ResourceDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<ResourceDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        ResourceExample example = new ResourceExample();
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        List<ResourceDTO> resourceDTOList = resourceList.stream().map(resource -> JSONUtil.toBean(JSONUtil.toJsonStr(resource), ResourceDTO.class)).collect(Collectors.toList());
        PageInfo<Resource> resourcePageInfo = new PageInfo<>(resourceList);
        PageResult<ResourceDTO> resourceDTOPageResult = new PageResult<>(resourcePageInfo.getTotal(), resourcePageInfo.getPages(), resourceDTOList);
        return resourceDTOPageResult;
    }

    /**
     * 保存
     * @param resourceDTO 章dto
     */
    public void save(ResourceDTO resourceDTO){
        Resource resource = JSONUtil.toBean(JSONUtil.toJsonStr(resourceDTO), Resource.class);
        if (StringUtils.isEmpty(resourceDTO.getId())) {
            this.insert(resource);
        }else {
            this.update(resource);
        }
    }

    /**
     * 更新
     *
     * @param resource 章
     */
    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 插入，id是自定义好的，不需要自动生成
     *
     * @param resource 章
     */
    private void insert(Resource resource){
        resourceMapper.insert(resource);
    }

    /**
     * 删除通过id
     *
     * @param resourceId 章id
     */
    public void deleteById(String resourceId) {
        resourceMapper.deleteByPrimaryKey(resourceId);
    }

    /**
     * 保存json
     *
     * @param jsonStr json str
     */
    public void saveJson(String jsonStr) {
        List<ResourceDTO> jsonList = JSON.parseArray(jsonStr, ResourceDTO.class);
        List<ResourceDTO> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDTO resourceDTO : jsonList) {
                resourceDTO.setParent("");
                add(list, resourceDTO);
            }
        }
        log.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);

        for (ResourceDTO resource : list) {
            this.insert(JSONUtil.toBean(JSONUtil.toJsonStr(resource), Resource.class));
        }
    }

    /**
     * 递归，将树形结构的节点全部取出来，放到list
     *
     * @param list        列表
     * @param resourceDTO 资源dto
     */
    private void add(List<ResourceDTO> list, ResourceDTO resourceDTO) {
        list.add(resourceDTO);
        if (!CollectionUtils.isEmpty(resourceDTO.getChildren())) {
            for (ResourceDTO child : resourceDTO.getChildren()) {
                child.setParent(resourceDTO.getId());
                add(list, child);
            }
        }
    }

    /**
     * 按约定将树转成列表
     * 要求：ID要正序排序
     *
     * @return {@link List<ResourceDTO>}
     */
    public List<ResourceDTO> loadTree() {
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        List<ResourceDTO> resourceDtoList = JSONUtil.toList(JSONUtil.parseArray(resourceList), ResourceDTO.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // 当前要移动的记录
            ResourceDTO child = resourceDtoList.get(i);

            // 如果当前节点没有父节点，则不用往下了
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            // 查找父节点
            for (int j = i - 1; j >= 0; j--) {
                ResourceDTO parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0, child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }
}