package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.dto.ExamPaperQueryDTO;
import com.example.wmsiescore.dto.ExamPaperSaveDTO;
import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.service.AdminExamPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员试卷控制器
 */
@RestController
@RequestMapping("/admin/exam-paper")
@Api(tags = "试卷管理", description = "试卷的增删改查操作")
public class AdminExamPaperController {
    
    @Autowired
    private AdminExamPaperService adminExamPaperService;
    
    /**
     * 分页查询试卷列表
     * 支持按name、status、validdpt、validsource、paper_type字段查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页查询试卷列表", notes = "支持按name、status、validdpt、validsource、paper_type字段查询")
    public ResponseResult<PageResult> getExamPaperList(
            @ApiParam(value = "查询条件") @RequestBody ExamPaperQueryDTO queryDTO) {
        PageResult<EtExamPaper> result = adminExamPaperService.getExamPaperList(queryDTO);
        return ResponseResult.success("查询成功", result);
    }
    
    /**
     * 保存试卷（创建/更新/删除）
     * 支持批量删除
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存试卷", notes = "创建、更新或删除试卷，支持批量删除")
    public ResponseResult<Boolean> saveExamPaper(
            @ApiParam(value = "试卷保存信息") @RequestBody ExamPaperSaveDTO examPaperSaveDTO) {
        Boolean result = adminExamPaperService.saveExamPaper(examPaperSaveDTO);
        return ResponseResult.success("操作成功", result);
    }
    
    /**
     * 批量删除试卷
     */
    @PostMapping("/batch-delete")
    @ApiOperation(value = "批量删除试卷")
    public ResponseResult<Boolean> deleteExamPapers(
            @ApiParam(value = "试卷ID列表") @RequestBody List<Long> ids) {
        Boolean result = adminExamPaperService.deleteExamPapers(ids);
        return ResponseResult.success("批量删除成功", result);
    }
}