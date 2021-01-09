package com.jiavideo.auth.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.auth.dto.RoleResourceDTO;
import com.jiavideo.auth.entity.RoleResource;
import com.jiavideo.auth.entity.RoleResourceExample;
import com.jiavideo.auth.mapper.RoleResourceMapper;
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
 * 角色资源服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleResourceServer {
    @Autowired(required = false)
    private RoleResourceMapper roleResourceMapper;

    /**
     * 查询所有
     * @return {@link List<RoleResourceDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<RoleResourceDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        RoleResourceExample example = new RoleResourceExample();
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<RoleResourceDTO> roleResourceDTOList = roleResourceList.stream().map(roleResource -> JSONUtil.toBean(JSONUtil.toJsonStr(roleResource), RoleResourceDTO.class)).collect(Collectors.toList());
        PageInfo<RoleResource> roleResourcePageInfo = new PageInfo<>(roleResourceList);
        PageResult<RoleResourceDTO> roleResourceDTOPageResult = new PageResult<>(roleResourcePageInfo.getTotal(), roleResourcePageInfo.getPages(), roleResourceDTOList);
        return roleResourceDTOPageResult;
    }

    /**
     * 保存
     * @param roleResourceDTO 角色资源dto
     */
    public void save(RoleResourceDTO roleResourceDTO){
        RoleResource roleResource = JSONUtil.toBean(JSONUtil.toJsonStr(roleResourceDTO), RoleResource.class);
        if (StringUtils.isEmpty(roleResourceDTO.getId())) {
            this.insert(roleResource);
        }else {
            this.update(roleResource);
        }
    }

    /**
     * 更新
     *
     * @param roleResource 角色资源
     */
    private void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    /**
     * 插入
     *
     * @param roleResource 角色资源
     */
    private void insert(RoleResource roleResource){
        roleResource.setId(UUIDUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }

    /**
     * 删除通过id
     *
     * @param roleResourceId 角色资源id
     */
    public void deleteById(String roleResourceId) {
        roleResourceMapper.deleteByPrimaryKey(roleResourceId);
    }
}