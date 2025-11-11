package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.*;
import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.dto.QuestionListDTO;
import com.example.wmsiescore.dto.QuestionQueryDTO;
import com.example.wmsiescore.dto.QuestionSaveDTO;
import com.example.wmsiescore.mapper.EtFieldMapper;
import com.example.wmsiescore.mapper.EtKnowledgePointMapper;
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
    private EtQuestionDao etQuestionDao;
    
    @Autowired
    private EtFieldMapper etFieldMapper;
    
    @Autowired
    private EtKnowledgePointMapper etKnowledgePointMapper;
    
    @Autowired
    private EtQuestion2PointDao etQuestion2PointDao;
    
    @Override
    public PageResult<QuestionListDTO> getQuestionList(QuestionQueryDTO queryDTO) {
        // 查询总记录数
        Long total = Long.valueOf(etQuestionDao.countQuestionListWithDetails(
                queryDTO.getQuestionName(),
                queryDTO.getQuestionTypeId(),
                queryDTO.getPointId(),
                queryDTO.getFieldId()
        ));
        
        // 如果没有数据，返回空分页结果
        if (total == null || total == 0) {
            return PageResult.empty(queryDTO.getPageNum(), queryDTO.getPageSize());
        }
        
        // 查询当前页数据
        List<QuestionListDTO> records = etQuestionDao.selectQuestionListWithDetails(
                queryDTO.getQuestionName(),
                queryDTO.getQuestionTypeId(),
                queryDTO.getPointId(),
                queryDTO.getFieldId(),
                (queryDTO.getPageNum() - 1) * queryDTO.getPageSize(),
                queryDTO.getPageSize()
        );
        
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
        return etFieldMapper.selectAll();
    }
    
    @Override
    public List<EtKnowledgePoint> getAllKnowledgePoints() {
        return etKnowledgePointMapper.selectAll();
    }
    
    /**
     * 根据条件统计试题数量
     */
    public Integer countQuestionsByConditions(QuestionQueryDTO queryDTO) {
        return etQuestionDao.countQuestionListWithDetails(
                queryDTO.getQuestionName(),
                queryDTO.getQuestionTypeId(),
                queryDTO.getPointId(),
                queryDTO.getFieldId()
        );
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
        return etQuestionDao.selectById(id);
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

            
            etQuestionDao.insert(question);
            
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
            
            etQuestionDao.updateById(question);
            
            // 删除原有知识点关联
            etQuestion2PointDao.deleteByQuestionId(questionSaveDTO.getId().intValue());
            
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
            etQuestion2PointDao.deleteByQuestionId(questionId.intValue());
            
            // 删除试题
            return etQuestionDao.deleteById(questionId) > 0;
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
            relation.setPointId(pointId.intValue());
            etQuestion2PointDao.insert(relation);
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
        return etQuestionDao.selectByTypeId(questionTypeId);
    }
    
    @Override
    public List<EtQuestion> getQuestionsByGroupId(Long groupId) {
        return etQuestionDao.selectByGroupId(groupId);
    }
}