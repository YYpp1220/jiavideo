package com.jiavideo.system.server;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.auth.dto.ResourceDTO;
import com.jiavideo.common.enumerate.ExceptionEnum;
import com.jiavideo.common.excepton.JvException;
import com.jiavideo.user.dto.LoginUserDTO;
import com.jiavideo.user.dto.UserDTO;
import com.jiavideo.user.entity.User;
import com.jiavideo.user.entity.UserExample;
import com.jiavideo.system.mapper.UserMapper;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class UserServer {
    @Autowired(required = false)
    private UserMapper userMapper;

    /**
     * 查询所有
     * @return {@link List<UserDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<UserDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        UserExample example = new UserExample();
        List<User> userList = userMapper.selectByExample(example);
        List<UserDTO> userDTOList = userList.stream().map(user -> JSONUtil.toBean(JSONUtil.toJsonStr(user), UserDTO.class)).collect(Collectors.toList());
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        PageResult<UserDTO> userDTOPageResult = new PageResult<>(userPageInfo.getTotal(), userPageInfo.getPages(), userDTOList);
        return userDTOPageResult;
    }

    /**
     * 保存
     * @param userDTO 章dto
     */
    public void save(UserDTO userDTO){
        User user = JSONUtil.toBean(JSONUtil.toJsonStr(userDTO), User.class);
        if (StringUtils.isEmpty(userDTO.getId())) {
            this.insert(user);
        }else {
            this.update(user);
        }
    }

    /**
     * 更新
     *
     * @param user 章
     */
    private void update(User user) {
        user.setPassword(null);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 插入
     *
     * @param user 章
     */
    private void insert(User user){
        user.setId(UUIDUtil.getShortUuid());
        User userDb = this.selectByLoginName(user.getLoginName());
        if (!StringUtils.isEmpty(userDb)) {
            throw new JvException(ExceptionEnum.USER_ALREADY_EXIST);
        }
        userMapper.insert(user);
    }

    /**
     * 删除通过id
     *
     * @param userId 章id
     */
    public void deleteById(String userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 判断当前登录的用户是否存在
     *
     * @param loginName 登录名
     * @return {@link User}
     */
    private User selectByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            throw new JvException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * 保存密码
     *
     * @param userDTO 用户dto
     */
    public void savePassword(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 登录
     *
     * @param userDTO 用户dto
     */
    public LoginUserDTO login(UserDTO userDTO) {
        User user = this.selectByLoginName(userDTO.getLoginName());
        if (StringUtils.isEmpty(user)) {
            log.error("用户名不存在！{}", userDTO.getLoginName());
            throw new JvException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        } else {
            if (user.getPassword().equals(userDTO.getPassword())) {
                LoginUserDTO loginUserDTO = JSONUtil.toBean(JSONUtil.toJsonStr(user), LoginUserDTO.class);
                setAuth(loginUserDTO);
                return loginUserDTO;
            } else {
                log.error("密码不正确！输入密码：{}，原密码：{}", userDTO.getPassword(), user.getPassword());
                throw new JvException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            }
        }
    }

    /**
     * 为登录用户获取资源权限
     *
     * @param loginUserDTO 登录用户dto
     */
    private void setAuth(LoginUserDTO loginUserDTO) {
        List<ResourceDTO> resourceDTOList = userMapper.findResources(loginUserDTO.getId());
        loginUserDTO.setResources(resourceDTOList);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceDTOList)) {
            resourceDTOList.forEach(resourceDTO -> {
                String requestArr = resourceDTO.getRequest();
                List<String> requestList = Convert.toList(String.class, requestArr);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            });
        }
        log.info("有权限的请求{}", requestSet);
        loginUserDTO.setRequests(requestSet);
    }
}