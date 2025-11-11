package com.example.wmsiescore.service;

import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.dto.KnowledgePointQueryDTO;
import com.example.wmsiescore.dto.KnowledgePointSaveDTO;
import com.example.wmsiescore.model.EtKnowledgePoint;

/**
 * 知识点管理服务接口
 */
public interface AdminKnowledgePointService {
    
    /**
     * 保存知识点（创建、更新、删除）
     */
    Boolean saveKnowledgePoint(KnowledgePointSaveDTO knowledgePointSaveDTO);
    
    /**
     * 根据ID查询知识点（内部使用）
     */
    EtKnowledgePoint selectById(Integer pointId);
}