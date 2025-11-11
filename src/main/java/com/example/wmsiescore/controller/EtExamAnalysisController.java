package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.model.ExamRanking;
import com.example.wmsiescore.model.ExamStatistics;
import com.example.wmsiescore.service.EtExamAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examAnalysis")
//@Api(tags = "考试结果分析接口", description = "考试成绩排名、统计和导出功能")
public class EtExamAnalysisController {
    @Autowired
    private EtExamAnalysisService etExamAnalysisService;

    @ApiOperation(value = "获取考试排名", notes = "获取指定考试的考试成绩排名")
    @PostMapping("/getRankings")
    public ResponseResult<List<ExamRanking>> getExamRankings(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getExamRankings(examPaperId));
    }

    @ApiOperation(value = "获取考试统计信息", notes = "获取指定考试的完整统计信息")
    @PostMapping("/getStatistics")
    public ResponseResult<ExamStatistics> getExamStatistics(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getExamStatistics(examPaperId));
    }

    @ApiOperation(value = "获取考试总人数", notes = "统计参与指定考试的总人数")
    @PostMapping("/getTotalParticipants")
    public ResponseResult<Integer> getTotalParticipants(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getTotalParticipants(examPaperId));
    }

    @ApiOperation(value = "获取实际参考人数", notes = "统计实际参加考试的人数")
    @PostMapping("/getActualParticipants")
    public ResponseResult<Integer> getActualParticipants(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getActualParticipants(examPaperId));
    }

    @ApiOperation(value = "获取参考名单", notes = "获取实际参加考试的人员名单")
    @PostMapping("/getParticipantList")
    public ResponseResult<List<EtUserExamHistory>> getParticipantList(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getParticipantList(examPaperId));
    }

    @ApiOperation(value = "获取缺考名单", notes = "获取未参加考试的人员名单")
    @PostMapping("/getAbsentList")
    public ResponseResult<List<EtUserExamHistory>> getAbsentList(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getAbsentList(examPaperId));
    }

    @ApiOperation(value = "获取平均分", notes = "计算指定考试的平均成绩")
    @PostMapping("/getAverageScore")
    public ResponseResult<java.math.BigDecimal> getAverageScore(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getAverageScore(examPaperId));
    }

    @ApiOperation(value = "获取员工考试时长", notes = "获取每位员工完成考试的实际用时")
    @PostMapping("/getUserExamDurations")
    public ResponseResult<List<EtUserExamHistory>> getUserExamDurations(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.getUserExamDurations(examPaperId));
    }

    @ApiOperation(value = "导出考试结果", notes = "导出考试统计结果用于外部文件")
    @PostMapping("/exportResults")
    public ResponseResult<List<EtUserExamHistory>> exportExamResults(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etExamAnalysisService.exportExamResults(examPaperId));
    }

    @ApiOperation(value = "记录用户考试历史", notes = "记录用户的考试历史信息")
    @PostMapping("/recordHistory")
    public ResponseResult<Long> recordUserExamHistory(@ApiParam(value = "考试历史信息", required = true) @RequestBody EtUserExamHistory history) {
        return ResponseResult.success(etExamAnalysisService.recordUserExamHistory(history));
    }

    @ApiOperation(value = "更新用户考试历史", notes = "更新用户的考试历史信息")
    @PostMapping("/updateHistory")
    public ResponseResult<Boolean> updateUserExamHistory(@ApiParam(value = "考试历史信息", required = true) @RequestBody EtUserExamHistory history) {
        return ResponseResult.success(etExamAnalysisService.updateUserExamHistory(history));
    }

    @ApiOperation(value = "获取用户考试历史", notes = "获取指定用户对指定试卷的考试历史")
    @PostMapping("/getUserHistory")
    public ResponseResult<EtUserExamHistory> getUserExamHistory(@ApiParam(value = "用户ID和试卷ID", required = true) @RequestBody java.util.Map<String, Long> params) {
        Long userId = params.get("userId");
        Long examPaperId = params.get("examPaperId");
        return ResponseResult.success(etExamAnalysisService.getUserExamHistory(userId, examPaperId));
    }
}