package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.service.EtExamPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/examPaper")
//@Api(tags = "试卷管理接口", description = "试卷的增删改查和权限管理")
public class EtExamPaperController {
    @Autowired
    private EtExamPaperService etExamPaperService;

    @ApiOperation(value = "创建试卷", notes = "管理员创建新的考试试卷")
    @PostMapping("/create")
    public ResponseResult<Long> createExamPaper(@ApiParam(value = "试卷信息", required = true) @RequestBody EtExamPaper examPaper) {
        return ResponseResult.success(etExamPaperService.createExamPaper(examPaper));
    }

    @ApiOperation(value = "更新试卷", notes = "更新已存在的试卷信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateExamPaper(@ApiParam(value = "试卷信息", required = true) @RequestBody EtExamPaper examPaper) {
        return ResponseResult.success(etExamPaperService.updateExamPaper(examPaper));
    }

    @ApiOperation(value = "删除试卷", notes = "根据ID删除指定的试卷")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.deleteExamPaper(id));
    }

    @ApiOperation(value = "获取试卷详情", notes = "根据ID获取试卷的详细信息")
    @PostMapping("/getById")
    public ResponseResult<EtExamPaper> getExamPaperById(@ApiParam(value = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.getExamPaperById(id));
    }

    @ApiOperation(value = "获取所有试卷列表", notes = "获取系统中所有试卷的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtExamPaper>> listAllExamPapers() {
        return ResponseResult.success(etExamPaperService.listAllExamPapers());
    }

    @ApiOperation(value = "发布试卷", notes = "将试卷状态设置为已发布")
    @PostMapping("/publish")
    public ResponseResult<Boolean> publishExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.publishExamPaper(id));
    }

    @ApiOperation(value = "取消发布试卷", notes = "将试卷状态设置为草稿")
    @PostMapping("/unpublish")
    public ResponseResult<Boolean> unpublishExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etExamPaperService.unpublishExamPaper(id));
    }

//    @ApiOperation(value = "获取用户已完成的试卷ID列表", notes = "获取用户已完成的试卷ID列表（提交时间不为空）")
//    @GetMapping("/getCompletedExamPaperIds/{userId}")
//    public ResponseResult<List<Long>> getCompletedExamPaperIds(@ApiParam(value = "用户ID", required = true) @PathVariable Long userId) {
//        return ResponseResult.success(etExamPaperService.getCompletedExamPaperIds(userId));
//    }
//
//    @ApiOperation(value = "获取用户可见的试卷列表", notes = "获取用户可见的试卷列表（is_visible=1，paper_status=1，且部门或群组匹配）")
//    @GetMapping("/getVisibleExamPapers/{userId}")
//    public ResponseResult<List<EtExamPaper>> getVisibleExamPapersForUser(@ApiParam(value = "用户ID", required = true) @PathVariable Long userId) {
//        return ResponseResult.success(etExamPaperService.getVisibleExamPapersForUser(userId));
//    }
//
//    @ApiOperation(value = "获取用户待参加的考试列表", notes = "获取用户待参加的考试列表（可见且未考过的试卷）")
//    @GetMapping("/getPendingExams/{userId}")
//    public ResponseResult<List<PendingExamDTO>> getPendingExamsForUser(@ApiParam(value = "用户ID", required = true) @PathVariable Long userId) {
//        return ResponseResult.success(etExamPaperService.getPendingExamsForUser(userId));
//    }

}