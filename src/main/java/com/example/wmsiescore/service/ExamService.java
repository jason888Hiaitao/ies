package com.example.wmsiescore.service;

import com.example.wmsiescore.model.ExamAnalysis;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.dto.ExamSubmissionDTO;
import com.example.wmsiescore.dto.ExamStartResult;

import java.util.List;
import java.util.Map;

public interface ExamService {

    /**
     * 获取用户待参加的考试列表
     * @param userId 用户ID
     * @return 待参加考试列表
     */
    List<PendingExamDTO> getPendingExamsForUser(Long userId);

    /**
     * 开始考试
     * @param userId 用户ID
     * @param examPaperId 试卷ID
     * @return 考试开始结果（包含考试记录ID和考题列表）
     */
    ExamStartResult startExam(Long userId, Long examPaperId);

    /**
     * 提交考试
     * @param recordId 考试记录ID
     * @param answers 答案JSON字符串
     * @return 提交是否成功
     */
    boolean submitExam(Long recordId, String answers);
    
    /**
     * 提交考试（使用DTO）
     * @param examSubmission 考试提交DTO
     * @return 提交是否成功
     */
    boolean submitExam(ExamSubmissionDTO examSubmission);
    
    /**
     * 提交考试（使用Map）
     * @param recordId 考试记录ID
     * @param userAnswers 用户答案Map
     * @return 提交是否成功
     */
    boolean submitExam(Long recordId, Map<Long, String> userAnswers);

    /**
     * 分析考试结果
     * @param recordId 考试记录ID
     * @return 考试分析结果
     */
    ExamAnalysis analyzeExam(Long recordId);

    /**
     * 获取题目详情
     * @param questionId 题目ID
     * @return 题目详情
     */
    Object getQuestionDetail(Long questionId);
}