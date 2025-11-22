package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.dto.ExamSubmissionDTO;
import com.example.wmsiescore.dto.ExamSubmitRequestDTO;
import com.example.wmsiescore.dto.QuestionAnswerDTO;
import com.example.wmsiescore.dto.ExamStartResult;
import com.example.wmsiescore.model.ExamAnalysis;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.service.ExamService;
import com.example.wmsiescore.service.PendingExamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 考试控制器
 * 提供考试相关的API接口
 */
@Slf4j
@RestController
@RequestMapping("/api/exam")
@Tag(name = "考试操作-对客接口", description = "考试开始、提交、分析等相关接口")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private PendingExamService pendingExamService;

    /**
     * 开始考试
     * @param userId 用户ID
     * @param examPaperId 试卷ID
     * @return 考试开始结果（包含考试记录ID和考题列表）
     */
    @PostMapping("/start")
    @Operation(summary = "开始考试", description = "用户开始指定试卷的考试，系统返回考试记录ID和考题列表")
    public ResponseEntity<ExamStartResult> startExam(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId, 
            @Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId) {
        try {
            ExamStartResult result = examService.startExam(userId, examPaperId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("开始考试失败，用户ID：{}，试卷ID：{}", userId, examPaperId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 提交考试
     * @param submitRequest 考试提交请求对象
     * @return 提交是否成功
     */
    @PostMapping("/submit")
    @Operation(summary = "提交考试", description = "用户提交考试答案，系统进行评分并保存考试记录")
    public ResponseEntity<Boolean> submitExam(
            @Parameter(description = "考试提交请求对象，包含考试记录ID和答案列表", required = true) @RequestBody ExamSubmitRequestDTO submitRequest) {
        try {
            // 转换为ExamSubmissionDTO进行业务处理
            ExamSubmissionDTO examSubmission = new ExamSubmissionDTO();
            examSubmission.setExamHistoryId(submitRequest.getExamHistoryId());
            examSubmission.setUserId(submitRequest.getUserId());
            examSubmission.setExamPaperId(submitRequest.getExamPaperId());
            examSubmission.setAnswers(submitRequest.getAnswers());

            boolean success = examService.submitExam(examSubmission);
            log.info("提交考试成功，考试记录ID：{}", submitRequest.getExamHistoryId());
            
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            log.error("提交考试失败，考试记录ID：{}", submitRequest.getExamHistoryId(), e);
            return ResponseEntity.badRequest().build();
        }
    }



    /**
     * 分析考试结果
     * @param examHistoryId 考试记录ID
     * @return 考试分析结果
     */
    @PostMapping("/analyze")
    @Operation(summary = "分析考试结果", description = "根据考试记录ID分析考试结果，包括得分、正确率等统计信息")
    public ResponseEntity<ExamAnalysis> analyzeExam(
            @Parameter(description = "考试记录ID", required = true) @RequestParam Long examHistoryId) {
        try {
            ExamAnalysis analysis = examService.analyzeExam(examHistoryId);
            return ResponseEntity.ok(analysis);
        } catch (Exception e) {
            log.error("分析考试结果失败，考试记录ID：{}", examHistoryId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "获取用户待参加的考试列表", description = "获取用户待参加的考试列表（可见且未考过的试卷）")
    @PostMapping("/getPendingExams")
    public ResponseResult<List<PendingExamDTO>> getPendingExamsForUser(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId) {
        return ResponseResult.success(pendingExamService.getPendingExamsForUser(userId));
    }
}
