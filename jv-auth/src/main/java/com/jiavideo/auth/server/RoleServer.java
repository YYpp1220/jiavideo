package com.jiavideo.auth.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.auth.dto.RoleDTO;
import com.jiavideo.auth.entity.*;
import com.jiavideo.auth.mapper.RoleMapper;
import com.jiavideo.auth.mapper.RoleResourceMapper;
import com.jiavideo.auth.mapper.RoleUserMapper;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务器的作用
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServer {
    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Autowired(required = false)
    private RoleResourceMapper roleResourceMapper;

    @Autowired(required = false)
    private RoleUserMapper roleUserMapper;

    /**
     * 查询所有
     * @return {@link List<RoleDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<RoleDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        RoleExample example = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(example);
        List<RoleDTO> roleDTOList = roleList.stream().map(role -> JSONUtil.toBean(JSONUtil.toJsonStr(role), RoleDTO.class)).collect(Collectors.toList());
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
        PageResult<RoleDTO> roleDTOPageResult = new PageResult<>(rolePageInfo.getTotal(), rolePageInfo.getPages(), roleDTOList);
        return roleDTOPageResult;
    }

    /**
     * 保存
     * @param roleDTO 角色dto
     */
    public void save(RoleDTO roleDTO){
        Role role = JSONUtil.toBean(JSONUtil.toJsonStr(roleDTO), Role.class);
        if (StringUtils.isEmpty(roleDTO.getId())) {
            this.insert(role);
        }else {
            this.update(role);
        }
    }

    /**
     * 更新
     *
     * @param role 角色
     */
    private void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 插入
     *
     * @param role 角色
     */
    private void insert(Role role){
        role.setId(UUIDUtil.getShortUuid());
        roleMapper.insert(role);
    }

    /**
     * 删除通过id
     *
     * @param roleId 角色id
     */
    public void deleteById(String roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 按角色保存资源
     *
     * @param roleDTO 角色dto
     */
    public void saveResource(RoleDTO roleDTO) {
        String roleId = roleDTO.getId();
        List<String> resourceIds = roleDTO.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        resourceIds.forEach(resourceId -> {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UUIDUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResourceMapper.insert(roleResource);
        });
    }

    /**
     * 按角色加载资源
     *
     * @param roleId 角色id
     * @return {@link List<String>}
     */
    public List<String> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        roleResourceList.forEach(roleResource -> {
            resourceIdList.add(roleResource.getResourceId());
        });
        return resourceIdList;
    }

    /**
     * 按角色保存用户，给当前用户赋予那些角色
     *
     * @param roleDTO 角色dto
     */
    public void saveUser(RoleDTO roleDTO) {
        String roleId = roleDTO.getId();
        List<String> userIds = roleDTO.getUserIds();
        // 清空库中所有的当前角色下的记录
        RoleUserExample example = new RoleUserExample();
        RoleUserExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(example);

        // 保存角色用户
        userIds.forEach(userId -> {
            RoleUser roleUser = new RoleUser();
            roleUser.setId(UUIDUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);
            roleUserMapper.insert(roleUser);
        });
    }

    /**
     * 按角色加载用户
     *
     * @param roleId 角色id
     * @return {@link List<String>}
     */
    public List<String> listUser(String roleId) {
        RoleUserExample example = new RoleUserExample();
        RoleUserExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        List<String> userIds = new ArrayList<>();
        roleUserList.forEach(roleUser -> {
            userIds.add(roleUser.getUserId());
        });
        return userIds;
    }
}