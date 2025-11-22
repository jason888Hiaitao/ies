package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUserTemporary;
import com.example.wmsiescore.service.EtUserTemporaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-temporary")
//@Tag(name = "用户群组管理", description = "临时用户群组的增删改查")
public class EtUserTemporaryController {

    @Autowired
    private EtUserTemporaryService etUserTemporaryService;

    @Operation(summary = "获取所有用户群组", description = "返回系统中所有用户群组的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUserTemporary>> findAll() {
        return ResponseResult.success(etUserTemporaryService.findAll());
    }

    @Operation(summary = "根据ID获取用户群组", description = "根据用户群组ID返回详情")
    @PostMapping("/getById")
    public ResponseResult<EtUserTemporary> findById(@Parameter(description = "用户群组ID", required = true) @RequestBody Integer id) {
        return ResponseResult.success(etUserTemporaryService.findById(id));
    }

    @Operation(summary = "新增用户群组", description = "创建一条新的用户群组记录")
    @PostMapping("/save")
    public ResponseResult<Integer> save(@Parameter(description = "用户群组信息", required = true) @RequestBody EtUserTemporary etUserTemporary) {
        return ResponseResult.success(etUserTemporaryService.save(etUserTemporary));
    }

    @Operation(summary = "更新用户群组", description = "根据ID更新用户群组记录")
    @PostMapping("/update")
    public ResponseResult<Integer> update(@Parameter(description = "用户群组信息", required = true) @RequestBody EtUserTemporary etUserTemporary) {
        return ResponseResult.success(etUserTemporaryService.update(etUserTemporary));
    }

    @Operation(summary = "删除用户群组", description = "根据ID删除用户群组记录")
    @PostMapping("/delete")
    public ResponseResult<Integer> delete(@Parameter(description = "用户群组ID", required = true) @RequestBody Integer id) {
        return ResponseResult.success(etUserTemporaryService.delete(id));
    }
}
