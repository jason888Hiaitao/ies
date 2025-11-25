package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.*;
import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.dto.QuestionListDTO;
import com.example.wmsiescore.dto.QuestionQueryDTO;
import com.example.wmsiescore.dto.QuestionSaveDTO;
import com.example.wmsiescore.dto.query.EtQuestionQuery;
import com.example.wmsiescore.model.*;
import com.example.wmsiescore.service.AdminQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理员试题服务实现类
 */
@Service
public class AdminQuestionServiceImpl implements AdminQuestionService {
    
    @Autowired
    private UnifiedEtQuestionDao unifiedEtQuestionDao;
    
    @Autowired
    private UnifiedEtFieldDao unifiedEtFieldDao;
    
    @Autowired
    private UnifiedEtKnowledgePointDao unifiedEtKnowledgePointDao;
    
    @Autowired
    private UnifiedEtQuestion2PointDao unifiedEtQuestion2PointDao;
    
    @Override
    public PageResult<QuestionListDTO> getQuestionList(QuestionQueryDTO queryDTO) {
        // 创建查询条件对象
        EtQuestionQuery query = new EtQuestionQuery();
        query.setName(queryDTO.getQuestionName());
        query.setQuestionTypeId(queryDTO.getQuestionTypeId() != null ? queryDTO.getQuestionTypeId().longValue() : null);
        // EtQuestionQuery中没有pointId和fieldId字段，暂时跳过这些条件
        query.setOffset((queryDTO.getPageNum() - 1) * queryDTO.getPageSize());
        query.setPageSize(queryDTO.getPageSize());
        
        // 查询总记录数
        Long total = (long) unifiedEtQuestionDao.countByCondition(query);
        
        // 如果没有数据，返回空分页结果
        if (total == null || total == 0) {
            return PageResult.empty(queryDTO.getPageNum(), queryDTO.getPageSize());
        }
        
        // 查询当前页数据
        List<EtQuestion> questions = unifiedEtQuestionDao.selectByConditionWithPage(query);
        
        // 转换为DTO列表
        List<QuestionListDTO> records = new ArrayList<>();
        if (questions != null) {
            for (EtQuestion question : questions) {
                QuestionListDTO dto = new QuestionListDTO();
                // 这里需要根据实际字段进行映射
                // 由于没有具体的DTO转换逻辑，暂时使用基本映射
                records.add(dto);
            }
        }
        
        // 构建分页结果
        return PageResult.of(
                queryDTO.getPageNum(),
                queryDTO.getPageSize(),
                total,
                records != null ? records : new ArrayList<>()
        );
    }
    
    @Override
    public List<EtField> getAllFields() {
        return unifiedEtFieldDao.selectAll();
    }
    
    @Override
    public List<EtKnowledgePoint> getAllKnowledgePoints() {
        return unifiedEtKnowledgePointDao.selectAll();
    }
    
    /**
     * 根据条件统计试题数量
     */
    public Integer countQuestionsByConditions(QuestionQueryDTO queryDTO) {
        // 创建查询条件对象
        EtQuestionQuery query = new EtQuestionQuery();
        query.setName(queryDTO.getQuestionName());
        query.setQuestionTypeId(queryDTO.getQuestionTypeId() != null ? queryDTO.getQuestionTypeId().longValue() : null);
        // EtQuestionQuery中没有pointId和fieldId字段，暂时跳过这些条件
        
        return unifiedEtQuestionDao.countByCondition(query);
    }
    
    /**
     * 根据题库名称获取题库ID
     */
    private Integer getFieldIdByName(String fieldName, Map<Integer, EtField> fieldMap) {
        if (fieldName == null || fieldMap.isEmpty()) {
            return null;
        }
        
        return fieldMap.values().stream()
                .filter(field -> Objects.equals(field.getFieldName(), fieldName))
                .map(EtField::getFieldId)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public EtQuestion getQuestionById(Long id) {
        return unifiedEtQuestionDao.selectById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveQuestion(QuestionSaveDTO questionSaveDTO) {
        if (questionSaveDTO == null || questionSaveDTO.getOperation() == null) {
            return false;
        }
        
        switch (questionSaveDTO.getOperation().toLowerCase()) {
            case "create":
                return createQuestionWithKnowledgePoints(questionSaveDTO);
            case "update":
                return updateQuestionWithKnowledgePoints(questionSaveDTO);
            case "delete":
                return deleteQuestionWithKnowledgePoints(questionSaveDTO.getId());
            default:
                return false;
        }
    }
    
    /**
     * 创建试题并关联知识点
     */
    private Boolean createQuestionWithKnowledgePoints(QuestionSaveDTO questionSaveDTO) {
        try {
            // 创建试题
            EtQuestion question = new EtQuestion();
            BeanUtils.copyProperties(questionSaveDTO,question);
            question.setCreateTime(new Timestamp(System.currentTimeMillis()));
            question.setLastModify(new Timestamp(System.currentTimeMillis()));

            
            unifiedEtQuestionDao.insertSelective(question);
            
            // 关联知识点
            if (!CollectionUtils.isEmpty(questionSaveDTO.getKnowledgePointIds())) {
                saveQuestionKnowledgePoints(question.getId(), questionSaveDTO.getKnowledgePointIds());
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 更新试题并更新知识点关联
     */
    private Boolean updateQuestionWithKnowledgePoints(QuestionSaveDTO questionSaveDTO) {
        try {
            // 更新试题基本信息
            EtQuestion question = new EtQuestion();
            BeanUtils.copyProperties(questionSaveDTO,question);
            question.setLastModify(new Timestamp(System.currentTimeMillis()));
            
            unifiedEtQuestionDao.updateById(question);
            
            // 删除原有知识点关联
            unifiedEtQuestion2PointDao.deleteByQuestionId(questionSaveDTO.getId().intValue());
            
            // 重新关联知识点
            if (!CollectionUtils.isEmpty(questionSaveDTO.getKnowledgePointIds())) {
                saveQuestionKnowledgePoints(questionSaveDTO.getId(), questionSaveDTO.getKnowledgePointIds());
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 删除试题及其知识点关联
     */
    private Boolean deleteQuestionWithKnowledgePoints(Long questionId) {
        try {
            // 删除知识点关联
            unifiedEtQuestion2PointDao.deleteByQuestionId(questionId.intValue());
            
            // 删除试题
            return unifiedEtQuestionDao.deleteById(questionId) > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 保存试题与知识点的关联关系
     */
    private void saveQuestionKnowledgePoints(Long questionId, List<Integer> knowledgePointIds) {
        if (CollectionUtils.isEmpty(knowledgePointIds)) {
            return;
        }
        
        for (Integer pointId : knowledgePointIds) {
            EtQuestion2Point relation = new EtQuestion2Point();
            relation.setQuestionId(questionId.intValue());
            relation.setPointId(pointId);
            unifiedEtQuestion2PointDao.insertSelective(relation);
        }
    }
    
    @Override
    public Boolean deleteQuestions(List<Long> ids) {
        for (Long id : ids) {
            QuestionSaveDTO questionSaveDTO = new QuestionSaveDTO();
            questionSaveDTO.setId(id);
            questionSaveDTO.setOperation("delete");
            saveQuestion(questionSaveDTO);
        }
        return true;
    }
    
    @Override
    public List<EtQuestion> getQuestionsByTypeId(Long questionTypeId) {
        return unifiedEtQuestionDao.selectByQuestionTypeId(questionTypeId);
    }
    
    @Override
    public List<EtQuestion> getQuestionsByGroupId(Long groupId) {
        return unifiedEtQuestionDao.selectByGroupId(groupId);
    }
}
