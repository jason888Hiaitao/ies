package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtComment;
import com.example.wmsiescore.service.EtCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
//@Api(tags = "评论管理", description = "评论的增删改查")
public class EtCommentController {

    @Autowired
    private EtCommentService etCommentService;

    @ApiOperation(value = "获取所有评论", notes = "返回系统中所有评论的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtComment>> findAll() {
        return ResponseResult.success(etCommentService.findAll());
    }

    @ApiOperation(value = "根据ID获取评论", notes = "根据评论ID返回评论详情")
    @PostMapping("/getById")
    public ResponseResult<EtComment> findById(@ApiParam(value = "评论ID", required = true) @RequestBody Map<String, Integer> request) {
        return ResponseResult.success(etCommentService.findById(request.get("commentId")));
    }

    @ApiOperation(value = "新增评论", notes = "创建一个新评论")
    @PostMapping("/save")
    public ResponseResult<Integer> save(@ApiParam(value = "评论信息", required = true) @RequestBody EtComment etComment) {
        return ResponseResult.success(etCommentService.save(etComment));
    }

    @ApiOperation(value = "更新评论", notes = "根据评论ID更新评论信息")
    @PostMapping("/update")
    public ResponseResult<Integer> update(@ApiParam(value = "评论信息", required = true) @RequestBody EtComment etComment) {
        return ResponseResult.success(etCommentService.update(etComment));
    }

    @ApiOperation(value = "删除评论", notes = "根据评论ID删除评论")
    @PostMapping("/delete")
    public ResponseResult<Integer> delete(@ApiParam(value = "评论ID", required = true) @RequestBody Map<String, Integer> request) {
        return ResponseResult.success(etCommentService.delete(request.get("commentId")));
    }
}