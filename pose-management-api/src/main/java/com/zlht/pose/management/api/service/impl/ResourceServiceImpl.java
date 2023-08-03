package com.zlht.pose.management.api.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlht.pose.management.api.enums.Status;
import com.zlht.pose.management.api.service.ResourceServiceI;
import com.zlht.pose.management.dao.entity.Resource;
import com.zlht.pose.management.dao.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ResourceServiceImpl extends BaseServiceImpl implements ResourceServiceI {


    private final String[] ACCEPT_TYPES = new String[]{"zip"};
    /**
     * 文件磁盘路径
     */
    @Value("${files.upload.local.path}")
    private String fileUploadPath;

    @Value("${files.upload.local.maxFileSize}")
    private long maxFileSize;

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public Map<String, Object> createResource(MultipartFile file) {

        //TODO 中文文件上传
        Map<String, Object> map = new HashMap<>();
        //获取文件原始名称
        String fullName = file.getOriginalFilename();

        //获取文件的类型
        String suffix = FileUtil.extName(fullName);
        if (!checkFileType(suffix)) {
            putMsg(map, 400, "不支持的文件类型！");
            return map;
        }
        long size = file.getSize();
        if (!checkFileSize(size)) {
            putMsg(map, 400, "文件过大,无法上传！");
            return map;
        }
        String uuid = UUID.randomUUID().toString();
        String fileName = fileUploadPath + uuid + StrUtil.DOT;
        Resource resource = new Resource(fullName, uuid, suffix, size, new Date(), new Date());
        resourceMapper.insert(resource);
        putMsg(map, Status.SUCCESS.getCode(), "上传成功");
        map.put("data", uuid);
        File uploadResource = new File(fileName + suffix);
        //将临时文件转存到指定磁盘位置
        try {
            file.transferTo(uploadResource);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteResource(String uuid) {

        Map<String, Object> map = new HashMap<>();
        if (!resourceExist(uuid)) {
            putMsg(map, 400, "删除文件不存在！");
            return map;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("alias", uuid);
        Resource resource = resourceMapper.selectOne(queryWrapper);
        //local
        File deleteResource = new File(fileUploadPath+uuid + "." + resource.getSuffix());
        boolean local_delete = deleteResource.delete();
        int delete = 0;
        if (local_delete) {
            //database
            delete = resourceMapper.delete(queryWrapper);
        }
        if (local_delete && delete > 0) {
            putMsg(map, Status.SUCCESS.getCode(), "删除成功！");
        } else {
            putMsg(map, 400, "刪除失败！");
        }
        return map;
    }


    @Override
    public ResponseEntity downloadResource(String uuid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("alias", uuid);
        Resource resource = resourceMapper.selectOne(queryWrapper);
        if (resource == null) {
            return ResponseEntity.badRequest().body("未找到资源！");
        }
        // 从文件存储中读取文件
        File file = new File(fileUploadPath + resource.getAlias() + ".zip");
        InputStreamResource inputStreamResource = null;
        try {
            inputStreamResource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 设置HTTP头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFullName().toLowerCase() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(inputStreamResource);
    }

    @Override
    public boolean checkFileType(String type) {
        if (type == null || type.equals("")) return false;
        for (String item : ACCEPT_TYPES) {
            if (item.equals(type.toLowerCase())) return true;
        }
        return false;
    }

    @Override
    public boolean checkFileSize(long size) {
        return size < maxFileSize ? true : false;
    }


    @Override
    public boolean resourceExist(String uuid) {
        return resourceMapper.resourceExist(uuid) != null;
    }
}