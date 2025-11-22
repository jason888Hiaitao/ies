package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dto.KnowledgePointSaveDTO;
import com.example.wmsiescore.dao.UnifiedEtKnowledgePointDao;
import com.example.wmsiescore.model.EtKnowledgePoint;
import com.example.wmsiescore.service.AdminKnowledgePointService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 知识点管理服务实现类
 */
@Service
public class AdminKnowledgePointServiceImpl implements AdminKnowledgePointService {
    
    @Autowired
    private UnifiedEtKnowledgePointDao unifiedEtKnowledgePointDao;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveKnowledgePoint(KnowledgePointSaveDTO knowledgePointSaveDTO) {
        try {
            String operation = knowledgePointSaveDTO.getOperation();
            
            if ("create".equals(operation)) {
                return createKnowledgePoint(knowledgePointSaveDTO);
            } else if ("update".equals(operation)) {
                return updateKnowledgePoint(knowledgePointSaveDTO);
            } else if ("delete".equals(operation)) {
                return deleteKnowledgePoint(knowledgePointSaveDTO.getPointId());
            } else if ("batch-delete".equals(operation)) {
                return batchDeleteKnowledgePoints(knowledgePointSaveDTO.getIds());
            } else {
                throw new IllegalArgumentException("不支持的操作类型: " + operation);
            }
        } catch (Exception e) {
            throw new RuntimeException("知识点操作失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 创建知识点
     */
    private Boolean createKnowledgePoint(KnowledgePointSaveDTO knowledgePointSaveDTO) {
        EtKnowledgePoint knowledgePoint = new EtKnowledgePoint();
        BeanUtils.copyProperties(knowledgePointSaveDTO, knowledgePoint);
        
        // 设置默认值
        if (knowledgePoint.getState() == null) {
            knowledgePoint.setState(BigDecimal.ONE); // 默认状态为1（正常）
        }
        
        int result = unifiedEtKnowledgePointDao.insertSelective(knowledgePoint);
        return result > 0;
    }
    
    /**
     * 更新知识点
     */
    private Boolean updateKnowledgePoint(KnowledgePointSaveDTO knowledgePointSaveDTO) {
        if (knowledgePointSaveDTO.getPointId() == null) {
            throw new IllegalArgumentException("更新操作必须提供ID");
        }
        
        EtKnowledgePoint knowledgePoint = new EtKnowledgePoint();
        BeanUtils.copyProperties(knowledgePointSaveDTO, knowledgePoint);
        
        int result = unifiedEtKnowledgePointDao.updateById(knowledgePoint);
        return result > 0;
    }
    
    /**
     * 删除知识点
     */
    private Boolean deleteKnowledgePoint(Integer pointId) {
        if (pointId == null) {
            throw new IllegalArgumentException("删除操作必须提供ID");
        }
        
        int result = unifiedEtKnowledgePointDao.deleteById(pointId);
        return result > 0;
    }
    
    /**
     * 批量删除知识点
     */
    private Boolean batchDeleteKnowledgePoints(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("批量删除操作必须提供ID列表");
        }
        
        int result = unifiedEtKnowledgePointDao.batchDelete(ids);
        return result > 0;
    }
    



    @Override
    public EtKnowledgePoint selectById(Integer pointId) {
        return unifiedEtKnowledgePointDao.selectById(pointId);
    }
}