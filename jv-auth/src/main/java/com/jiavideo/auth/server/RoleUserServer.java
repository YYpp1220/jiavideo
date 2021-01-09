package com.jiavideo.auth.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.auth.dto.RoleUserDTO;
import com.jiavideo.auth.entity.RoleUser;
import com.jiavideo.auth.entity.RoleUserExample;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色用户服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleUserServer {
    @Autowired(required = false)
    private RoleUserMapper roleUserMapper;

    /**
     * 查询所有
     * @return {@link List<RoleUserDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<RoleUserDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        RoleUserExample example = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        List<RoleUserDTO> roleUserDTOList = roleUserList.stream().map(roleUser -> JSONUtil.toBean(JSONUtil.toJsonStr(roleUser), RoleUserDTO.class)).collect(Collectors.toList());
        PageInfo<RoleUser> roleUserPageInfo = new PageInfo<>(roleUserList);
        PageResult<RoleUserDTO> roleUserDTOPageResult = new PageResult<>(roleUserPageInfo.getTotal(), roleUserPageInfo.getPages(), roleUserDTOList);
        return roleUserDTOPageResult;
    }

    /**
     * 保存
     * @param roleUserDTO 角色用户dto
     */
    public void save(RoleUserDTO roleUserDTO){
        RoleUser roleUser = JSONUtil.toBean(JSONUtil.toJsonStr(roleUserDTO), RoleUser.class);
        if (StringUtils.isEmpty(roleUserDTO.getId())) {
            this.insert(roleUser);
        }else {
            this.update(roleUser);
        }
    }

    /**
     * 更新
     *
     * @param roleUser 角色用户
     */
    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 插入
     *
     * @param roleUser 角色用户
     */
    private void insert(RoleUser roleUser){
        roleUser.setId(UUIDUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }

    /**
     * 删除通过id
     *
     * @param roleUserId 角色用户id
     */
    public void deleteById(String roleUserId) {
        roleUserMapper.deleteByPrimaryKey(roleUserId);
    }
}