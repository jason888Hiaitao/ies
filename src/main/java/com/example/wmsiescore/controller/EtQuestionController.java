package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtExamPaperQuestion;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.service.EtQuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
//@Tag(name = "试题与试卷管理接口", description = "试题的增删改查和试卷分类管理")
public class EtQuestionController {
    @Autowired
    private EtQuestionService etQuestionService;

    @Operation(summary = "添加试题", description = "管理员添加新的试题")
    @PostMapping("/add")
    public ResponseResult<Long> addQuestion(@Parameter(description = "试题信息", required = true) @RequestBody EtQuestion question) {
        return ResponseResult.success(etQuestionService.addQuestion(question));
    }

    @Operation(summary = "编辑试题", description = "更新已存在的试题信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateQuestion(@Parameter(description = "试题信息", required = true) @RequestBody EtQuestion question) {
        return ResponseResult.success(etQuestionService.updateQuestion(question));
    }

    @Operation(summary = "删除试题", description = "根据ID删除指定的试题")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteQuestion(@Parameter(description = "试题ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etQuestionService.deleteQuestion(id));
    }

    @Operation(summary = "获取试题详情", description = "根据ID获取试题的详细信息")
    @PostMapping("/getById")
    public ResponseResult<EtQuestion> getQuestionById(@Parameter(description = "试题ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etQuestionService.getQuestionById(id));
    }

    @Operation(summary = "按分组获取试题列表", description = "根据分组ID获取试题列表")
    @PostMapping("/getByGroupId")
    public ResponseResult<List<EtQuestion>> getQuestionsByGroupId(@Parameter(description = "分组ID", required = true) @RequestBody Long groupId) {
        return ResponseResult.success(etQuestionService.getQuestionsByGroupId(groupId));
    }

    @Operation(summary = "按题型获取试题列表", description = "根据题型ID获取试题列表")
    @PostMapping("/getByType")
    public ResponseResult<List<EtQuestion>> getQuestionsByType(@Parameter(description = "题型ID", required = true) @RequestBody Long questionTypeId) {
        return ResponseResult.success(etQuestionService.getQuestionsByType(questionTypeId));
    }

    @Operation(summary = "获取所有试题列表", description = "获取系统中所有试题的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtQuestion>> getAllQuestions() {
        return ResponseResult.success(etQuestionService.getAllQuestions());
    }

    @Operation(summary = "搜索试题", description = "根据关键词搜索试题")
    @PostMapping("/search")
    public ResponseResult<List<EtQuestion>> searchQuestions(@Parameter(description = "搜索关键词", required = true) @RequestBody String keyword) {
        return ResponseResult.success(etQuestionService.searchQuestions(keyword));
    }

    @Operation(summary = "获取可见试题列表", description = "获取系统中所有可见的试题列表")
    @PostMapping("/getVisible")
    public ResponseResult<List<EtQuestion>> getVisibleQuestions() {
        return ResponseResult.success(etQuestionService.getVisibleQuestions());
    }

    @Operation(summary = "按难度获取试题列表", description = "根据难度获取试题列表")
    @PostMapping("/getByDifficulty")
    public ResponseResult<List<EtQuestion>> getQuestionsByDifficulty(@Parameter(description = "难度", required = true) @RequestBody String difficulty) {
        return ResponseResult.success(etQuestionService.getQuestionsByDifficulty(difficulty));
    }

    @Operation(summary = "按创建人获取试题列表", description = "根据创建人获取试题列表")
    @PostMapping("/getByCreator")
    public ResponseResult<List<EtQuestion>> getQuestionsByCreator(@Parameter(description = "创建人", required = true) @RequestBody String creator) {
        return ResponseResult.success(etQuestionService.getQuestionsByCreator(creator));
    }

    @Operation(summary = "更新试题统计信息", description = "更新试题的曝光次数、答对次数、答错次数")
    @PostMapping("/updateStatistics")
    public ResponseResult<Boolean> updateQuestionStatistics(@Parameter(description = "试题ID", required = true) @RequestParam Long id,
                                                           @Parameter(description = "曝光次数", required = false) @RequestParam(required = false) Integer exposeTimes,
                                                           @Parameter(description = "答对次数", required = false) @RequestParam(required = false) Integer rightTimes,
                                                           @Parameter(description = "答错次数", required = false) @RequestParam(required = false) Integer wrongTimes) {
        return ResponseResult.success(etQuestionService.updateQuestionStatistics(id, exposeTimes, rightTimes, wrongTimes));
    }

    @Operation(summary = "设置试题可见性", description = "设置试题的可见状态")
    @PostMapping("/setVisibility")
    public ResponseResult<Boolean> setQuestionVisibility(@Parameter(description = "试题ID", required = true) @RequestParam Long id,
                                                        @Parameter(description = "是否可见", required = true) @RequestParam Boolean isVisible) {
        return ResponseResult.success(etQuestionService.setQuestionVisibility(id, isVisible));
    }

    @Operation(summary = "添加试题到试卷", description = "将指定试题添加到试卷中")
    @PostMapping("/addQuestionToExamPaper")
    public ResponseResult<Boolean> addQuestionToExamPaper(@Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                         @Parameter(description = "试题ID", required = true) @RequestParam Long questionId,
                                                         @Parameter(description = "分值", required = false) @RequestParam(required = false) Integer score) {
        return ResponseResult.success(etQuestionService.addQuestionToExamPaper(examPaperId, questionId, score));
    }

    @Operation(summary = "从试卷中移除试题", description = "将指定试题从试卷中移除")
    @PostMapping("/removeQuestionFromExamPaper")
    public ResponseResult<Boolean> removeQuestionFromExamPaper(@Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                              @Parameter(description = "试题ID", required = true) @RequestParam Long questionId) {
        return ResponseResult.success(etQuestionService.removeQuestionFromExamPaper(examPaperId, questionId));
    }

    @Operation(summary = "获取试卷中的试题列表", description = "获取指定试卷中的所有试题")
    @PostMapping("/getQuestionsByExamPaper")
    public ResponseResult<List<EtExamPaperQuestion>> getQuestionsByExamPaper(@Parameter(description = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etQuestionService.getQuestionsByExamPaper(examPaperId));
    }

    @Operation(summary = "批量添加试题到试卷", description = "将多个试题批量添加到试卷中")
    @PostMapping("/batchAddQuestionsToExamPaper")
    public ResponseResult<Boolean> batchAddQuestionsToExamPaper(@Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                               @Parameter(description = "试题ID列表", required = true) @RequestBody List<Long> questionIds) {
        return ResponseResult.success(etQuestionService.batchAddQuestionsToExamPaper(examPaperId, questionIds));
    }

    @Operation(summary = "批量移除试题从试卷", description = "将多个试题批量从试卷中移除")
    @PostMapping("/batchRemoveQuestionsFromExamPaper")
    public ResponseResult<Boolean> batchRemoveQuestionsFromExamPaper(@Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                                    @Parameter(description = "试题ID列表", required = true) @RequestBody List<Long> questionIds) {
        return ResponseResult.success(etQuestionService.batchRemoveQuestionsFromExamPaper(examPaperId, questionIds));
    }

    @Operation(summary = "调整试题在试卷中的顺序", description = "调整试题在试卷中的显示顺序")
    @PostMapping("/adjustQuestionOrder")
    public ResponseResult<Boolean> adjustQuestionOrder(@Parameter(description = "试卷试题关联ID", required = true) @RequestParam Long id, 
                                                       @Parameter(description = "排序号", required = true) @RequestParam Integer sortOrder) {
        return ResponseResult.success(etQuestionService.adjustQuestionOrder(id, sortOrder));
    }

    @Operation(summary = "设置试卷权限", description = "设置试卷的可见范围权限")
    @PostMapping("/setExamPaperPermission")
    public ResponseResult<Boolean> setExamPaperPermission(@Parameter(description = "试卷ID", required = true) @RequestParam Long examPaperId,
                                                          @Parameter(description = "目标群组ID", required = false) @RequestParam(required = false) String targetGroupId,
                                                          @Parameter(description = "目标用户ID", required = false) @RequestParam(required = false) String targetUserId) {
        return ResponseResult.success(etQuestionService.setExamPaperPermission(examPaperId, targetGroupId, targetUserId));
    }

    
}
