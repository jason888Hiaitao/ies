package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.dto.ExamHistoryQueryDTO;
import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.service.AdminExamHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员考试历史控制器
 */
@RestController
@RequestMapping("/admin/exam-history")
@Tag(name = "考试历史管理", description = "考试历史的查询操作")
public class AdminExamHistoryController {
    
    @Autowired
    private AdminExamHistoryService adminExamHistoryService;
    
    /**
     * 分页查询考试历史列表
     * 支持按试卷名、部门、团队字段查询
     */
    @PostMapping("/list")
    @Operation(summary = "分页查询考试历史列表", description = "支持按试卷名、部门、团队字段查询")
    public ResponseResult<PageResult> getExamHistoryList(
            @Parameter(description = "查询条件") @RequestBody ExamHistoryQueryDTO queryDTO) {
        PageResult result = adminExamHistoryService.getExamHistoryList(queryDTO);
        return ResponseResult.success("查询成功", result);
    }
}
