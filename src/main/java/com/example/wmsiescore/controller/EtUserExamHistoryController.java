package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.service.EtUserExamHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-exam-histories")
//@Tag(name = "用户考试历史管理", description = "用户考试历史的增删改查")
public class EtUserExamHistoryController {

    @Autowired
    private EtUserExamHistoryService etUserExamHistoryService;

    @Operation(summary = "获取所有用户考试历史", description = "返回系统中所有用户考试历史的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUserExamHistory>> findAll() {
        return ResponseResult.success(etUserExamHistoryService.findAll());
    }

    @Operation(summary = "根据ID获取用户考试历史", description = "根据考试历史ID返回详情")
    @PostMapping("/getById")
    public ResponseResult<EtUserExamHistory> findById(@Parameter(description = "考试历史ID", required = true) @RequestBody Map<String, Long> request) {
        return ResponseResult.success(etUserExamHistoryService.findById(request.get("id")));
    }

    @Operation(summary = "新增用户考试历史", description = "创建一条新的用户考试历史记录")
    @PostMapping("/save")
    public ResponseResult<Integer> save(@Parameter(description = "用户考试历史信息", required = true) @RequestBody EtUserExamHistory etUserExamHistory) {
        return ResponseResult.success(etUserExamHistoryService.save(etUserExamHistory));
    }

    @Operation(summary = "更新用户考试历史", description = "根据ID更新用户考试历史记录")
    @PostMapping("/update")
    public ResponseResult<Integer> update(@Parameter(description = "用户考试历史信息", required = true) @RequestBody EtUserExamHistory etUserExamHistory) {
        return ResponseResult.success(etUserExamHistoryService.update(etUserExamHistory));
    }

    @Operation(summary = "删除用户考试历史", description = "根据ID删除用户考试历史记录")
    @PostMapping("/delete")
    public ResponseResult<Integer> delete(@Parameter(description = "考试历史ID", required = true) @RequestBody Map<String, Long> request) {
        return ResponseResult.success(etUserExamHistoryService.delete(request.get("id")));
    }

    @Operation(summary = "获取用户考试历史", description = "根据用户ID获取该用户的所有考试历史记录")
    @PostMapping("/getUserHistory")
    public ResponseResult<List<EtUserExamHistory>> getUserExamHistory(@Parameter(description = "用户ID", required = true) @RequestBody Map<String, Long> request) {
        return ResponseResult.success(etUserExamHistoryService.getUserExamHistory(request.get("userId")));
    }

    @Operation(summary = "获取用户已完成的考试历史", description = "根据用户ID获取该用户已完成的考试历史记录")
    @PostMapping("/getUserCompletedHistory")
    public ResponseResult<List<EtUserExamHistory>> getUserCompletedExamHistory(@Parameter(description = "用户ID", required = true) @RequestBody Map<String, Long> request) {
        return ResponseResult.success(etUserExamHistoryService.getUserExamHistoryWithSubmitTime(request.get("userId")));
    }

    @Operation(summary = "获取用户对指定试卷的考试次数", description = "统计用户对指定试卷的考试次数")
    @PostMapping("/getUserAttempts")
    public ResponseResult<Integer> getUserAttempts(@Parameter(description = "用户ID", required = true) @RequestParam Long userId,
                                                 @Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId) {
        return ResponseResult.success(etUserExamHistoryService.countUserAttempts(userId, examPaperId));
    }

    @Operation(summary = "获取用户已完成的试卷ID列表", description = "获取用户已完成的试卷ID列表")
    @PostMapping("/getUserCompletedExamPapers")
    public ResponseResult<List<Long>> getUserCompletedExamPapers(@Parameter(description = "用户ID", required = true) @RequestBody Map<String, Long> request) {
        return ResponseResult.success(etUserExamHistoryService.getCompletedExamPaperIds(request.get("userId")));
    }
}
