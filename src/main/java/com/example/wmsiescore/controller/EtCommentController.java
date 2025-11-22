package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtComment;
import com.example.wmsiescore.service.EtCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
//@Tag(name = "评论管理", description = "评论的增删改查")
public class EtCommentController {

    @Autowired
    private EtCommentService etCommentService;

    @Operation(summary = "获取所有评论", description = "返回系统中所有评论的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtComment>> findAll() {
        return ResponseResult.success(etCommentService.findAll());
    }

    @Operation(summary = "根据ID获取评论", description = "根据评论ID返回评论详情")
    @PostMapping("/getById")
    public ResponseResult<EtComment> findById(@Parameter(description = "评论ID", required = true) @RequestBody Map<String, Integer> request) {
        return ResponseResult.success(etCommentService.findById(request.get("commentId")));
    }

    @Operation(summary = "新增评论", description = "创建一个新评论")
    @PostMapping("/save")
    public ResponseResult<Integer> save(@Parameter(description = "评论信息", required = true) @RequestBody EtComment etComment) {
        return ResponseResult.success(etCommentService.save(etComment));
    }

    @Operation(summary = "更新评论", description = "根据评论ID更新评论信息")
    @PostMapping("/update")
    public ResponseResult<Integer> update(@Parameter(description = "评论信息", required = true) @RequestBody EtComment etComment) {
        return ResponseResult.success(etCommentService.update(etComment));
    }

    @Operation(summary = "删除评论", description = "根据评论ID删除评论")
    @PostMapping("/delete")
    public ResponseResult<Integer> delete(@Parameter(description = "评论ID", required = true) @RequestBody Map<String, Integer> request) {
        return ResponseResult.success(etCommentService.delete(request.get("commentId")));
    }
}
