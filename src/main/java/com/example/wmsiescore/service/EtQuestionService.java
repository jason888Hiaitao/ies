package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtExamPaperQuestion;
import com.example.wmsiescore.model.EtQuestion;

import java.util.List;

public interface EtQuestionService {
    /**
     * 添加试题
     */
    Long addQuestion(EtQuestion question);

    /**
     * 编辑试题
     */
    Boolean updateQuestion(EtQuestion question);

    /**
     * 删除试题
     */
    Boolean deleteQuestion(Long id);

    /**
     * 获取试题详情
     */
    EtQuestion getQuestionById(Long id);

    /**
     * 按分组获取试题列表
     */
    List<EtQuestion> getQuestionsByGroupId(Long groupId);

    /**
     * 按题型获取试题列表
     */
    List<EtQuestion> getQuestionsByType(Long questionTypeId);

    /**
     * 获取所有试题列表
     */
    List<EtQuestion> getAllQuestions();

    /**
     * 获取可见试题列表
     */
    List<EtQuestion> getVisibleQuestions();

    /**
     * 搜索试题
     */
    List<EtQuestion> searchQuestions(String keyword);

    /**
     * 按难度获取试题列表
     */
    List<EtQuestion> getQuestionsByDifficulty(String difficulty);

    /**
     * 按创建人获取试题列表
     */
    List<EtQuestion> getQuestionsByCreator(String creator);

    /**
     * 更新试题统计信息
     */
    Boolean updateQuestionStatistics(Long id, Integer exposeTimes, Integer rightTimes, Integer wrongTimes);

    /**
     * 设置试题可见性
     */
    Boolean setQuestionVisibility(Long id, Boolean isVisible);

    /**
     * 添加试题到试卷
     */
    Boolean addQuestionToExamPaper(Long examPaperId, Long questionId, Integer score);

    /**
     * 从试卷中移除试题
     */
    Boolean removeQuestionFromExamPaper(Long examPaperId, Long questionId);

    /**
     * 获取试卷中的试题列表
     */
    List<EtExamPaperQuestion> getQuestionsByExamPaper(Long examPaperId);

    /**
     * 批量添加试题到试卷
     */
    Boolean batchAddQuestionsToExamPaper(Long examPaperId, List<Long> questionIds);

    /**
     * 批量移除试题从试卷
     */
    Boolean batchRemoveQuestionsFromExamPaper(Long examPaperId, List<Long> questionIds);

    /**
     * 调整试题在试卷中的顺序
     */
    Boolean adjustQuestionOrder(Long examPaperQuestionId, Integer sortOrder);

    /**
     * 设置试卷权限
     */
    Boolean setExamPaperPermission(Long examPaperId, String targetGroupId, String targetUserId);
}