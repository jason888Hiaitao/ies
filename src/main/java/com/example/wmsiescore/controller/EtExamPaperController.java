package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.service.EtExamPaperService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/examPaper")
//@Tag(name = "试卷管理接口", description = "试卷的增删改查和权限管理")
public class EtExamPaperController {
    @Autowired
    private EtExamPaperService etExamPaperService;

    @Operation(summary = "创建试卷", description = "管理员创建新的考试试卷")
    @PostMapping("/create")
    public ResponseResult<Long> createExamPaper(@Parameter(description = "试卷信息", required = true) @RequestBody EtExamPaper examPaper) {
        return ResponseResult.success(etExamPaperService.createExamPaper(examPaper));
    }

    @Operation(summary = "更新试卷", description = "更新已存在的试卷信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateExamPaper(@Parameter(description = "试卷信息", required = true) @RequestBody EtExamPaper examPaper) {
        return ResponseResult.success(etExamPaperService.updateExamPaper(examPaper));
    }

    @Operation(summary = "删除试卷", description = "根据ID删除指定的试卷")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteExamPaper(@Parameter(description = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.deleteExamPaper(id));
    }

    @Operation(summary = "获取试卷详情", description = "根据ID获取试卷的详细信息")
    @PostMapping("/getById")
    public ResponseResult<EtExamPaper> getExamPaperById(@Parameter(description = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.getExamPaperById(id));
    }

    @Operation(summary = "获取所有试卷列表", description = "获取系统中所有试卷的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtExamPaper>> listAllExamPapers() {
        return ResponseResult.success(etExamPaperService.listAllExamPapers());
    }

    @Operation(summary = "发布试卷", description = "将试卷状态设置为已发布")
    @PostMapping("/publish")
    public ResponseResult<Boolean> publishExamPaper(@Parameter(description = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.publishExamPaper(id));
    }

    @Operation(summary = "取消发布试卷", description = "将试卷状态设置为草稿")
    @PostMapping("/unpublish")
    public ResponseResult<Boolean> unpublishExamPaper(@Parameter(description = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.unpublishExamPaper(id));
    }

//    @Operation(summary = "获取用户已完成的试卷ID列表", description = "获取用户已完成的试卷ID列表（提交时间不为空）")
//    @GetMapping("/getCompletedExamPaperIds/{userId}")
//    public ResponseResult<List<Long>> getCompletedExamPaperIds(@Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
//        return ResponseResult.success(etExamPaperService.getCompletedExamPaperIds(userId));
//    }
//
//    @Operation(summary = "获取用户可见的试卷列表", description = "获取用户可见的试卷列表（is_visible=1，paper_status=1，且部门或群组匹配）")
//    @GetMapping("/getVisibleExamPapers/{userId}")
//    public ResponseResult<List<EtExamPaper>> getVisibleExamPapersForUser(@Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
//        return ResponseResult.success(etExamPaperService.getVisibleExamPapersForUser(userId));
//    }
//
//    @Operation(summary = "获取用户待参加的考试列表", description = "获取用户待参加的考试列表（可见且未考过的试卷）")
//    @GetMapping("/getPendingExams/{userId}")
//    public ResponseResult<List<PendingExamDTO>> getPendingExamsForUser(@Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
//        return ResponseResult.success(etExamPaperService.getPendingExamsForUser(userId));
//    }

}
