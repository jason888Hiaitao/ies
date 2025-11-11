package com.example.wmsiescore.service;

import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.dto.QuestionListDTO;
import com.example.wmsiescore.dto.QuestionQueryDTO;
import com.example.wmsiescore.dto.QuestionSaveDTO;
import com.example.wmsiescore.model.EtField;
import com.example.wmsiescore.model.EtKnowledgePoint;
import com.example.wmsiescore.model.EtQuestion;

import java.util.List;

/**
 * 管理员试题服务接口
 */
public interface AdminQuestionService {
    
    /**
     * 分页查询试题列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageResult<QuestionListDTO> getQuestionList(QuestionQueryDTO queryDTO);
    
    /**
     * 获取所有题库
     * @return 题库列表
     */
    List<EtField> getAllFields();
    
    /**
     * 获取所有知识点
     * @return 知识点列表
     */
    List<EtKnowledgePoint> getAllKnowledgePoints();
    
    /**
     * 根据ID查询试题
     * @param id 试题ID
     * @return 试题详情
     */
    EtQuestion getQuestionById(Long id);
    
    /**
     * 保存试题（创建/更新/删除）
     * @param questionSaveDTO 试题保存信息
     * @return 操作结果
     */
    Boolean saveQuestion(QuestionSaveDTO questionSaveDTO);
    
    /**
     * 批量删除试题
     * @param ids 试题ID列表
     * @return 是否成功
     */
    Boolean deleteQuestions(List<Long> ids);
    
    /**
     * 根据试题类型查询试题
     * @param questionTypeId 试题类型ID
     * @return 试题列表
     */
    List<EtQuestion> getQuestionsByTypeId(Long questionTypeId);
    
    /**
     * 根据分组ID查询试题
     * @param groupId 分组ID
     * @return 试题列表
     */
    List<EtQuestion> getQuestionsByGroupId(Long groupId);
}