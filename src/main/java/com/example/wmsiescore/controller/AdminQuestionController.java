package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.dto.*;
import com.example.wmsiescore.model.EtField;
import com.example.wmsiescore.model.EtKnowledgePoint;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.service.AdminFieldService;
import com.example.wmsiescore.service.AdminKnowledgePointService;
import com.example.wmsiescore.service.AdminQuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员试题控制器
 */
@RestController
@RequestMapping("/admin/question")
@Tag(name = "试题管理", description = "试题、题库和知识点的管理接口")
public class AdminQuestionController {

    @Autowired
    private AdminQuestionService adminQuestionService;

    @Autowired
    private AdminFieldService adminFieldService;

    @Autowired
    private AdminKnowledgePointService adminKnowledgePointService;

    /**
     * 查询试题列表
     * 支持按照试题名称、试题类型、题库、知识点条件进行分页查询
     */
    @PostMapping("/list")
    @Operation(summary = "查询试题列表", description = "支持按照试题名称、试题类型、题库、知识点条件进行分页查询")
    public ResponseResult<PageResult<QuestionListDTO>> getQuestionList(
            @Parameter(description = "查询条件") @RequestBody QuestionQueryDTO queryDTO) {
        PageResult<QuestionListDTO> result = adminQuestionService.getQuestionList(queryDTO);
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 返回所有题库
     */
    @PostMapping("/fields")
    @Operation(summary = "获取所有题库", description = "返回所有可用的题库列表")
    public ResponseResult<List<EtField>> getAllFields() {
        List<EtField> result = adminQuestionService.getAllFields();
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 返回所有知识点
     */
    @PostMapping("/knowledge-points")
    @Operation(summary = "获取所有知识点", description = "返回所有可用的知识点列表")
    public ResponseResult<List<EtKnowledgePoint>> getAllKnowledgePoints() {
        List<EtKnowledgePoint> result = adminQuestionService.getAllKnowledgePoints();
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 根据ID查询试题详情
     */
    @PostMapping("/detail")
    @Operation(summary = "查询试题详情", description = "根据试题ID获取试题详细信息")
    public ResponseResult<EtQuestion> getQuestionById(
            @Parameter(description = "试题ID") @RequestParam Long id) {
        EtQuestion result = adminQuestionService.getQuestionById(id);
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 保存试题（创建/更新/删除）
     * 支持试题与知识点的关联操作
     */
    @PostMapping("/save")
    @Operation(summary = "保存试题", description = "创建、更新或删除试题，支持试题与知识点的关联操作")
    public ResponseResult<Boolean> saveQuestion(
            @Parameter(description = "试题保存信息") @RequestBody QuestionSaveDTO questionSaveDTO) {
        Boolean result = adminQuestionService.saveQuestion(questionSaveDTO);
        return ResponseResult.success("操作成功", result);
    }

    /**
     * 批量删除试题
     */
    @PostMapping("/batch-delete")
    @Operation(summary = "批量删除试题", description = "根据试题ID列表批量删除试题")
    public ResponseResult<Boolean> deleteQuestions(
            @Parameter(description = "试题ID列表") @RequestBody List<Long> ids) {
        Boolean result = adminQuestionService.deleteQuestions(ids);
        return ResponseResult.success("批量删除成功", result);
    }

    // ==================== 题库操作接口 ====================

    /**
     * 题库操作接口 - 支持题库的增删改、批量删除
     */
    @PostMapping("/field/save")
    @Operation(summary = "保存题库", description = "支持题库的创建、更新、删除和批量删除操作")
    public ResponseResult<Boolean> saveField(
            @Parameter(description = "题库保存信息") @RequestBody FieldSaveDTO fieldSaveDTO) {
        try {
            Boolean result = adminFieldService.saveField(fieldSaveDTO);
            return ResponseResult.success("题库操作成功", result);
        } catch (Exception e) {
            return ResponseResult.error("题库操作异常：" + e.getMessage());
        }
    }


    // ==================== 知识点操作接口 ====================

    /**
     * 知识点操作接口 - 支持知识点的增删改、批量删除
     */
    @PostMapping("/knowledge-point/save")
    @Operation(summary = "保存知识点", description = "支持知识点的创建、更新、删除和批量删除操作")
    public ResponseResult<Boolean> saveKnowledgePoint(
            @Parameter(description = "知识点保存信息") @RequestBody KnowledgePointSaveDTO knowledgePointSaveDTO) {
        try {
            Boolean result = adminKnowledgePointService.saveKnowledgePoint(knowledgePointSaveDTO);
            return ResponseResult.success("知识点操作成功", result);
        } catch (Exception e) {
            return ResponseResult.error("知识点操作异常：" + e.getMessage());
        }
    }


}
