package com.jiavideo.${module}.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.${module}.dto.${Entity}DTO;
import com.jiavideo.${module}.entity.${Entity};
import com.jiavideo.${module}.entity.${Entity}Example;
import com.jiavideo.${module}.mapper.${Entity}Mapper;
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
<#list typeSet as type>
    <#if type == 'Date'>
import java.util.Date;
    </#if>
</#list>

/**
 * 章服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${Entity}Server {
    @Autowired(required = false)
    private ${Entity}Mapper ${entity}Mapper;

    /**
     * 查询所有
     * @return {@link List<${Entity}DTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<${Entity}DTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        ${Entity}Example example = new ${Entity}Example();
        <#list fieldList as field>
            <#if field.nameHump == 'sort'>
        example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Entity}> ${entity}List = ${entity}Mapper.selectByExample(example);
        List<${Entity}DTO> ${entity}DTOList = ${entity}List.stream().map(${entity} -> JSONUtil.toBean(JSONUtil.toJsonStr(${entity}), ${Entity}DTO.class)).collect(Collectors.toList());
        PageInfo<${Entity}> ${entity}PageInfo = new PageInfo<>(${entity}List);
        PageResult<${Entity}DTO> ${entity}DTOPageResult = new PageResult<>(${entity}PageInfo.getTotal(), ${entity}PageInfo.getPages(), ${entity}DTOList);
        return ${entity}DTOPageResult;
    }

    /**
     * 保存
     * @param ${entity}DTO 章dto
     */
    public void save(${Entity}DTO ${entity}DTO){
        ${Entity} ${entity} = JSONUtil.toBean(JSONUtil.toJsonStr(${entity}DTO), ${Entity}.class);
        if (StringUtils.isEmpty(${entity}DTO.getId())) {
            this.insert(${entity});
        }else {
            this.update(${entity});
        }
    }

    /**
     * 更新
     *
     * @param ${entity} 章
     */
    private void update(${Entity} ${entity}) {
        <#list fieldList as field>
            <#if field.nameHump == 'updatedAt'>
        ${entity}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${entity}Mapper.updateByPrimaryKey(${entity});
    }

    /**
     * 插入
     *
     * @param ${entity} 章
     */
    private void insert(${Entity} ${entity}){
        <#list fieldList as field>
            <#if field.nameHump == 'createdAt'>
        ${entity}.setCreatedAt(new Date());
            </#if>
            <#if field.nameHump == 'updatedAt'>
        ${entity}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${entity}.setId(UUIDUtil.getShortUuid());
        ${entity}Mapper.insert(${entity});
    }

    /**
     * 删除通过id
     *
     * @param ${entity}Id 章id
     */
    public void deleteById(String ${entity}Id) {
        ${entity}Mapper.deleteByPrimaryKey(${entity}Id);
    }
}