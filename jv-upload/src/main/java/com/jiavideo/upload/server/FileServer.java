package com.jiavideo.upload.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.file.dto.FileDTO;
import com.jiavideo.file.entity.File;
import com.jiavideo.file.entity.FileExample;
import com.jiavideo.upload.mapper.FileMapper;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

/**
 * 文件服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServer {
    @Autowired(required = false)
    private FileMapper fileMapper;

    /**
     * 查询所有
     * @return {@link List<FileDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<FileDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        FileExample example = new FileExample();
        List<File> fileList = fileMapper.selectByExample(example);
        List<FileDTO> fileDTOList = fileList.stream().map(file -> JSONUtil.toBean(JSONUtil.toJsonStr(file), FileDTO.class)).collect(Collectors.toList());
        PageInfo<File> filePageInfo = new PageInfo<>(fileList);
        PageResult<FileDTO> fileDTOPageResult = new PageResult<>(filePageInfo.getTotal(), filePageInfo.getPages(), fileDTOList);
        return fileDTOPageResult;
    }

    /**
     * 保存
     * @param fileDTO 文件dto
     */
    public void save(FileDTO fileDTO){
        File file = JSONUtil.toBean(JSONUtil.toJsonStr(fileDTO), File.class);
        File fileDb = selectByKey(fileDTO.getKey());
        if (StringUtils.isEmpty(fileDb)) {
            this.insert(file);
        }else {
            fileDb.setShardIndex(fileDTO.getShardIndex());
            this.update(fileDb);
        }
    }

    /**
     * 更新
     *
     * @param file 文件
     */
    private void update(File file) {
        file.setUpdatedAt(new Date());
        fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 插入
     *
     * @param file 文件
     */
    private void insert(File file){
        file.setCreatedAt(new Date());
        file.setUpdatedAt(new Date());
        file.setId(UUIDUtil.getShortUuid());
        fileMapper.insert(file);
    }

    /**
     * 删除通过id
     *
     * @param fileId 文件id
     */
    public void deleteById(String fileId) {
        fileMapper.deleteByPrimaryKey(fileId);
    }

    /**
     * 选择的关键
     *
     * @param key 关键
     * @return {@link File}
     */
    public File selectByKey(String key) {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andKeyEqualTo(key);
        List<File> files = fileMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(files)) {
            return null;
        }
        return files.get(0);
    }

    /**
     * 找到的关键
     *
     * @param key 关键
     * @return {@link FileDTO}
     */
    public FileDTO findByKey(String key) {
        File file = selectByKey(key);
        return JSONUtil.toBean(JSONUtil.toJsonStr(file), FileDTO.class);
    }
}