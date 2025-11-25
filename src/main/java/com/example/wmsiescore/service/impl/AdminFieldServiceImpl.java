package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dto.FieldSaveDTO;
import com.example.wmsiescore.dao.UnifiedEtFieldDao;
import com.example.wmsiescore.model.EtField;
import com.example.wmsiescore.service.AdminFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 题库管理服务实现类
 */
@Service
public class AdminFieldServiceImpl implements AdminFieldService {
    
    @Autowired
    private UnifiedEtFieldDao unifiedEtFieldDao;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveField(FieldSaveDTO fieldSaveDTO) {
        try {
            String operation = fieldSaveDTO.getOperation();
            
            if ("create".equals(operation)) {
                return createField(fieldSaveDTO);
            } else if ("update".equals(operation)) {
                return updateField(fieldSaveDTO);
            } else if ("delete".equals(operation)) {
                return deleteField(fieldSaveDTO.getFieldId());
            } else if ("batch-delete".equals(operation)) {
                return batchDeleteFields(fieldSaveDTO.getIds());
            } else {
                throw new IllegalArgumentException("不支持的操作类型: " + operation);
            }
        } catch (Exception e) {
            throw new RuntimeException("题库操作失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 创建题库
     */
    private Boolean createField(FieldSaveDTO fieldSaveDTO) {
        EtField field = new EtField();
        BeanUtils.copyProperties(fieldSaveDTO, field);
        
        // 设置默认值
        if (field.getState() == null) {
            field.setState(BigDecimal.ONE); // 默认状态为1（正常）
        }
        
        int result = unifiedEtFieldDao.insertSelective(field);
        return result > 0;
    }
    
    /**
     * 更新题库
     */
    private Boolean updateField(FieldSaveDTO fieldSaveDTO) {
        if (fieldSaveDTO.getFieldId() == null) {
            throw new IllegalArgumentException("更新操作必须提供ID");
        }
        
        EtField field = new EtField();
        BeanUtils.copyProperties(fieldSaveDTO, field);
        
        int result = unifiedEtFieldDao.updateById(field);
        return result > 0;
    }
    
    /**
     * 删除题库
     */
    private Boolean deleteField(Integer fieldId) {
        if (fieldId == null) {
            throw new IllegalArgumentException("删除操作必须提供ID");
        }
        
        int result = unifiedEtFieldDao.deleteById(fieldId);
        return result > 0;
    }
    
    /**
     * 批量删除题库
     */
    private Boolean batchDeleteFields(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("批量删除操作必须提供ID列表");
        }
        
        int result = unifiedEtFieldDao.batchDelete(ids);
        return result > 0;
    }
    

    
    @Override
    public EtField getFieldById(Integer fieldId) {
        if (fieldId == null) {
            throw new IllegalArgumentException("ID不能为空");
        }
        
        EtField field = unifiedEtFieldDao.selectById(fieldId);
        if (field == null) {
            throw new RuntimeException("题库不存在");
        }
        
        return field;
    }
    
    @Override
    public EtField selectById(Integer fieldId) {
        return unifiedEtFieldDao.selectById(fieldId);
    }
}
