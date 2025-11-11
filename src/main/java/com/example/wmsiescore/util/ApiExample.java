package com.example.wmsiescore.util;

import com.example.wmsiescore.dto.ExamSubmissionDTO;
import com.example.wmsiescore.dto.ExamSubmitRequestDTO;
import com.example.wmsiescore.dto.QuestionAnswerDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API调用示例工具类
 * 展示如何使用新创建的DTO类进行考试提交
 */
public class ApiExample {

    /**
     * 示例1：使用ExamSubmitRequestDTO提交考试
     */
    public static void exampleSubmitExam() {
        // 创建答案列表
        List<QuestionAnswerDTO> answers = Arrays.asList(
            new QuestionAnswerDTO(1L, "A"),      // 单选题
            new QuestionAnswerDTO(2L, "B,C"),    // 多选题
            new QuestionAnswerDTO(3L, "Java"),    // 填空题
            new QuestionAnswerDTO(4L, "面向对象编程是一种编程范式...") // 问答题
        );

        // 创建考试提交请求DTO
        ExamSubmitRequestDTO submitRequest = new ExamSubmitRequestDTO();
        submitRequest.setExamHistoryId(123L);
        submitRequest.setUserId(456L);
        submitRequest.setExamPaperId(789L);
        submitRequest.setAnswers(answers);

        // 调用API提交考试
        // POST /api/exam/submit
        // Content-Type: application/json
        // Body: submitRequest对象
    }

    /**
     * 示例3：使用Map格式提交考试
     */
    public static void exampleSubmitExamWithMap() {
        // 创建答案Map
        Map<Long, String> userAnswers = new HashMap<>();
        userAnswers.put(1L, "A");      // 题目ID -> 答案
        userAnswers.put(2L, "B,C");    // 多选题答案
        userAnswers.put(3L, "Java");    // 填空题答案
        userAnswers.put(4L, "面向对象编程是一种编程范式..."); // 问答题答案

        // 调用API提交考试
        // POST /api/exam/submit/map?recordId=123
        // Content-Type: application/json
        // Body: userAnswers对象
    }

    /**
     * 示例4：获取待参加考试列表
     */
    public static void exampleGetPendingExams() {
        // 调用API获取待参加考试
        // GET /api/exam/pending/456
        // 返回：List<PendingExamDTO>
    }

    /**
     * 示例5：开始考试
     */
    public static void exampleStartExam() {
        // 调用API开始考试
        // POST /api/exam/start?userId=456&examPaperId=789
        // 返回：考试记录ID
    }

    /**
     * 示例6：分析考试结果
     */
    public static void exampleAnalyzeExam() {
        // 调用API分析考试结果
        // GET /api/exam/analyze/123
        // 返回：ExamAnalysis对象
    }
}