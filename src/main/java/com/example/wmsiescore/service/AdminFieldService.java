package com.example.wmsiescore.service;

import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.dto.FieldQueryDTO;
import com.example.wmsiescore.dto.FieldSaveDTO;
import com.example.wmsiescore.model.EtField;

/**
 * 题库管理服务接口
 */
public interface AdminFieldService {
    
    /**
     * 保存题库（创建、更新、删除）
     */
    Boolean saveField(FieldSaveDTO fieldSaveDTO);
    

    
    /**
     * 根据ID查询题库详情
     */
    EtField getFieldById(Integer fieldId);
    
    /**
     * 根据ID查询题库（内部使用）
     */
    EtField selectById(Integer fieldId);
}