package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.dto.*;
import com.example.wmsiescore.model.EtField;
import com.example.wmsiescore.model.EtKnowledgePoint;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.service.AdminFieldService;
import com.example.wmsiescore.service.AdminKnowledgePointService;
import com.example.wmsiescore.service.AdminQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员试题控制器
 */
@RestController
@RequestMapping("/admin/question")
@Api(tags = "试题管理", description = "试题、题库和知识点的管理接口")
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
    @ApiOperation(value = "查询试题列表", notes = "支持按照试题名称、试题类型、题库、知识点条件进行分页查询")
    public ResponseResult<PageResult<QuestionListDTO>> getQuestionList(
            @ApiParam(value = "查询条件") @RequestBody QuestionQueryDTO queryDTO) {
        PageResult<QuestionListDTO> result = adminQuestionService.getQuestionList(queryDTO);
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 返回所有题库
     */
    @PostMapping("/fields")
    @ApiOperation(value = "获取所有题库", notes = "返回所有可用的题库列表")
    public ResponseResult<List<EtField>> getAllFields() {
        List<EtField> result = adminQuestionService.getAllFields();
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 返回所有知识点
     */
    @PostMapping("/knowledge-points")
    @ApiOperation(value = "获取所有知识点", notes = "返回所有可用的知识点列表")
    public ResponseResult<List<EtKnowledgePoint>> getAllKnowledgePoints() {
        List<EtKnowledgePoint> result = adminQuestionService.getAllKnowledgePoints();
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 根据ID查询试题详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "查询试题详情", notes = "根据试题ID获取试题详细信息")
    public ResponseResult<EtQuestion> getQuestionById(
            @ApiParam(value = "试题ID") @RequestParam Long id) {
        EtQuestion result = adminQuestionService.getQuestionById(id);
        return ResponseResult.success("查询成功", result);
    }

    /**
     * 保存试题（创建/更新/删除）
     * 支持试题与知识点的关联操作
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存试题", notes = "创建、更新或删除试题，支持试题与知识点的关联操作")
    public ResponseResult<Boolean> saveQuestion(
            @ApiParam(value = "试题保存信息") @RequestBody QuestionSaveDTO questionSaveDTO) {
        Boolean result = adminQuestionService.saveQuestion(questionSaveDTO);
        return ResponseResult.success("操作成功", result);
    }

    /**
     * 批量删除试题
     */
    @PostMapping("/batch-delete")
    @ApiOperation(value = "批量删除试题", notes = "根据试题ID列表批量删除试题")
    public ResponseResult<Boolean> deleteQuestions(
            @ApiParam(value = "试题ID列表") @RequestBody List<Long> ids) {
        Boolean result = adminQuestionService.deleteQuestions(ids);
        return ResponseResult.success("批量删除成功", result);
    }

    // ==================== 题库操作接口 ====================

    /**
     * 题库操作接口 - 支持题库的增删改、批量删除
     */
    @PostMapping("/field/save")
    @ApiOperation(value = "保存题库", notes = "支持题库的创建、更新、删除和批量删除操作")
    public ResponseResult<Boolean> saveField(
            @ApiParam(value = "题库保存信息") @RequestBody FieldSaveDTO fieldSaveDTO) {
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
    @ApiOperation(value = "保存知识点", notes = "支持知识点的创建、更新、删除和批量删除操作")
    public ResponseResult<Boolean> saveKnowledgePoint(
            @ApiParam(value = "知识点保存信息") @RequestBody KnowledgePointSaveDTO knowledgePointSaveDTO) {
        try {
            Boolean result = adminKnowledgePointService.saveKnowledgePoint(knowledgePointSaveDTO);
            return ResponseResult.success("知识点操作成功", result);
        } catch (Exception e) {
            return ResponseResult.error("知识点操作异常：" + e.getMessage());
        }
    }


}