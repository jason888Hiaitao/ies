package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtExamPaperQuestion;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.service.EtQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
//@Api(tags = "试题与试卷管理接口", description = "试题的增删改查和试卷分类管理")
public class EtQuestionController {
    @Autowired
    private EtQuestionService etQuestionService;

    @ApiOperation(value = "添加试题", notes = "管理员添加新的试题")
    @PostMapping("/add")
    public ResponseResult<Long> addQuestion(@ApiParam(value = "试题信息", required = true) @RequestBody EtQuestion question) {
        return ResponseResult.success(etQuestionService.addQuestion(question));
    }

    @ApiOperation(value = "编辑试题", notes = "更新已存在的试题信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateQuestion(@ApiParam(value = "试题信息", required = true) @RequestBody EtQuestion question) {
        return ResponseResult.success(etQuestionService.updateQuestion(question));
    }

    @ApiOperation(value = "删除试题", notes = "根据ID删除指定的试题")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteQuestion(@ApiParam(value = "试题ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etQuestionService.deleteQuestion(id));
    }

    @ApiOperation(value = "获取试题详情", notes = "根据ID获取试题的详细信息")
    @PostMapping("/getById")
    public ResponseResult<EtQuestion> getQuestionById(@ApiParam(value = "试题ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etQuestionService.getQuestionById(id));
    }

    @ApiOperation(value = "按分组获取试题列表", notes = "根据分组ID获取试题列表")
    @PostMapping("/getByGroupId")
    public ResponseResult<List<EtQuestion>> getQuestionsByGroupId(@ApiParam(value = "分组ID", required = true) @RequestBody Long groupId) {
        return ResponseResult.success(etQuestionService.getQuestionsByGroupId(groupId));
    }

    @ApiOperation(value = "按题型获取试题列表", notes = "根据题型ID获取试题列表")
    @PostMapping("/getByType")
    public ResponseResult<List<EtQuestion>> getQuestionsByType(@ApiParam(value = "题型ID", required = true) @RequestBody Long questionTypeId) {
        return ResponseResult.success(etQuestionService.getQuestionsByType(questionTypeId));
    }

    @ApiOperation(value = "获取所有试题列表", notes = "获取系统中所有试题的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtQuestion>> getAllQuestions() {
        return ResponseResult.success(etQuestionService.getAllQuestions());
    }

    @ApiOperation(value = "搜索试题", notes = "根据关键词搜索试题")
    @PostMapping("/search")
    public ResponseResult<List<EtQuestion>> searchQuestions(@ApiParam(value = "搜索关键词", required = true) @RequestBody String keyword) {
        return ResponseResult.success(etQuestionService.searchQuestions(keyword));
    }

    @ApiOperation(value = "获取可见试题列表", notes = "获取系统中所有可见的试题列表")
    @PostMapping("/getVisible")
    public ResponseResult<List<EtQuestion>> getVisibleQuestions() {
        return ResponseResult.success(etQuestionService.getVisibleQuestions());
    }

    @ApiOperation(value = "按难度获取试题列表", notes = "根据难度获取试题列表")
    @PostMapping("/getByDifficulty")
    public ResponseResult<List<EtQuestion>> getQuestionsByDifficulty(@ApiParam(value = "难度", required = true) @RequestBody String difficulty) {
        return ResponseResult.success(etQuestionService.getQuestionsByDifficulty(difficulty));
    }

    @ApiOperation(value = "按创建人获取试题列表", notes = "根据创建人获取试题列表")
    @PostMapping("/getByCreator")
    public ResponseResult<List<EtQuestion>> getQuestionsByCreator(@ApiParam(value = "创建人", required = true) @RequestBody String creator) {
        return ResponseResult.success(etQuestionService.getQuestionsByCreator(creator));
    }

    @ApiOperation(value = "更新试题统计信息", notes = "更新试题的曝光次数、答对次数、答错次数")
    @PostMapping("/updateStatistics")
    public ResponseResult<Boolean> updateQuestionStatistics(@ApiParam(value = "试题ID", required = true) @RequestParam Long id,
                                                           @ApiParam(value = "曝光次数", required = false) @RequestParam(required = false) Integer exposeTimes,
                                                           @ApiParam(value = "答对次数", required = false) @RequestParam(required = false) Integer rightTimes,
                                                           @ApiParam(value = "答错次数", required = false) @RequestParam(required = false) Integer wrongTimes) {
        return ResponseResult.success(etQuestionService.updateQuestionStatistics(id, exposeTimes, rightTimes, wrongTimes));
    }

    @ApiOperation(value = "设置试题可见性", notes = "设置试题的可见状态")
    @PostMapping("/setVisibility")
    public ResponseResult<Boolean> setQuestionVisibility(@ApiParam(value = "试题ID", required = true) @RequestParam Long id,
                                                        @ApiParam(value = "是否可见", required = true) @RequestParam Boolean isVisible) {
        return ResponseResult.success(etQuestionService.setQuestionVisibility(id, isVisible));
    }

    @ApiOperation(value = "添加试题到试卷", notes = "将指定试题添加到试卷中")
    @PostMapping("/addQuestionToExamPaper")
    public ResponseResult<Boolean> addQuestionToExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                         @ApiParam(value = "试题ID", required = true) @RequestParam Long questionId,
                                                         @ApiParam(value = "分值", required = false) @RequestParam(required = false) Integer score) {
        return ResponseResult.success(etQuestionService.addQuestionToExamPaper(examPaperId, questionId, score));
    }

    @ApiOperation(value = "从试卷中移除试题", notes = "将指定试题从试卷中移除")
    @PostMapping("/removeQuestionFromExamPaper")
    public ResponseResult<Boolean> removeQuestionFromExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                              @ApiParam(value = "试题ID", required = true) @RequestParam Long questionId) {
        return ResponseResult.success(etQuestionService.removeQuestionFromExamPaper(examPaperId, questionId));
    }

    @ApiOperation(value = "获取试卷中的试题列表", notes = "获取指定试卷中的所有试题")
    @PostMapping("/getQuestionsByExamPaper")
    public ResponseResult<List<EtExamPaperQuestion>> getQuestionsByExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestBody Long examPaperId) {
        return ResponseResult.success(etQuestionService.getQuestionsByExamPaper(examPaperId));
    }

    @ApiOperation(value = "批量添加试题到试卷", notes = "将多个试题批量添加到试卷中")
    @PostMapping("/batchAddQuestionsToExamPaper")
    public ResponseResult<Boolean> batchAddQuestionsToExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                               @ApiParam(value = "试题ID列表", required = true) @RequestBody List<Long> questionIds) {
        return ResponseResult.success(etQuestionService.batchAddQuestionsToExamPaper(examPaperId, questionIds));
    }

    @ApiOperation(value = "批量移除试题从试卷", notes = "将多个试题批量从试卷中移除")
    @PostMapping("/batchRemoveQuestionsFromExamPaper")
    public ResponseResult<Boolean> batchRemoveQuestionsFromExamPaper(@ApiParam(value = "试卷ID", required = true) @RequestParam Long examPaperId, 
                                                                    @ApiParam(value = "试题ID列表", required = true) @RequestBody List<Long> questionIds) {
        return ResponseResult.success(etQuestionService.batchRemoveQuestionsFromExamPaper(examPaperId, questionIds));
    }

    @ApiOperation(value = "调整试题在试卷中的顺序", notes = "调整试题在试卷中的显示顺序")
    @PostMapping("/adjustQuestionOrder")
    public ResponseResult<Boolean> adjustQuestionOrder(@ApiParam(value = "试卷试题关联ID", required = true) @RequestParam Long id, 
                                                       @ApiParam(value = "排序号", required = true) @RequestParam Integer sortOrder) {
        return ResponseResult.success(etQuestionService.adjustQuestionOrder(id, sortOrder));
    }

    @ApiOperation(value = "设置试卷权限", notes = "设置试卷的可见范围权限")
    @PostMapping("/setExamPaperPermission")
    public ResponseResult<Boolean> setExamPaperPermission(@ApiParam(value = "试卷ID", required = true) @RequestParam Long examPaperId,
                                                          @ApiParam(value = "目标群组ID", required = false) @RequestParam(required = false) String targetGroupId,
                                                          @ApiParam(value = "目标用户ID", required = false) @RequestParam(required = false) String targetUserId) {
        return ResponseResult.success(etQuestionService.setExamPaperPermission(examPaperId, targetGroupId, targetUserId));
    }

    
}