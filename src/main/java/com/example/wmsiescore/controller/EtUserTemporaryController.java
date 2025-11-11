package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUserTemporary;
import com.example.wmsiescore.service.EtUserTemporaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-temporary")
//@Api(tags = "用户群组管理", description = "临时用户群组的增删改查")
public class EtUserTemporaryController {

    @Autowired
    private EtUserTemporaryService etUserTemporaryService;

    @ApiOperation(value = "获取所有用户群组", notes = "返回系统中所有用户群组的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUserTemporary>> findAll() {
        return ResponseResult.success(etUserTemporaryService.findAll());
    }

    @ApiOperation(value = "根据ID获取用户群组", notes = "根据用户群组ID返回详情")
    @PostMapping("/getById")
    public ResponseResult<EtUserTemporary> findById(@ApiParam(value = "用户群组ID", required = true) @RequestBody Integer id) {
        return ResponseResult.success(etUserTemporaryService.findById(id));
    }

    @ApiOperation(value = "新增用户群组", notes = "创建一条新的用户群组记录")
    @PostMapping("/save")
    public ResponseResult<Integer> save(@ApiParam(value = "用户群组信息", required = true) @RequestBody EtUserTemporary etUserTemporary) {
        return ResponseResult.success(etUserTemporaryService.save(etUserTemporary));
    }

    @ApiOperation(value = "更新用户群组", notes = "根据ID更新用户群组记录")
    @PostMapping("/update")
    public ResponseResult<Integer> update(@ApiParam(value = "用户群组信息", required = true) @RequestBody EtUserTemporary etUserTemporary) {
        return ResponseResult.success(etUserTemporaryService.update(etUserTemporary));
    }

    @ApiOperation(value = "删除用户群组", notes = "根据ID删除用户群组记录")
    @PostMapping("/delete")
    public ResponseResult<Integer> delete(@ApiParam(value = "用户群组ID", required = true) @RequestBody Integer id) {
        return ResponseResult.success(etUserTemporaryService.delete(id));
    }
}