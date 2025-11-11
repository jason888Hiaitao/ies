package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUser;
import com.example.wmsiescore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理接口", description = "用户的增删改查和权限管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "设置管理员权限", notes = "设置用户是否为管理员")
    @PostMapping("/setAdmin")
    public ResponseResult<Boolean> setAdmin(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId, 
                                            @ApiParam(value = "是否为管理员", required = true) @RequestParam Boolean isAdmin) {
        return ResponseResult.success(userService.setAdmin(userId, isAdmin));
    }

    @ApiOperation(value = "设置用户禁用状态", notes = "设置用户是否被禁用")
    @PostMapping("/setDisabled")
    public ResponseResult<Boolean> setDisabled(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId, 
                                                @ApiParam(value = "是否禁用", required = true) @RequestParam Boolean isDisabled) {
        return ResponseResult.success(userService.setDisabled(userId, isDisabled));
    }

    @ApiOperation(value = "添加用户", notes = "创建新的用户")
    @PostMapping("/add")
    public ResponseResult<Long> addUser(@ApiParam(value = "用户信息", required = true) @RequestBody EtUser user) {
        return ResponseResult.success(userService.addUser(user));
    }

    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteUser(@ApiParam(value = "用户ID", required = true) @RequestBody Long userId) {
        return ResponseResult.success(userService.deleteUser(userId));
    }

    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateUser(@ApiParam(value = "用户信息", required = true) @RequestBody EtUser user) {
        return ResponseResult.success(userService.updateUser(user));
    }

    @ApiOperation(value = "获取用户", notes = "根据ID获取用户信息")
    @PostMapping("/getById")
    public ResponseResult<EtUser> getUser(@ApiParam(value = "用户ID", required = true) @RequestBody Long userId) {
        return ResponseResult.success(userService.getUser(userId));
    }

    @ApiOperation(value = "获取用户列表", notes = "获取所有用户列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUser>> listUsers() {
        return ResponseResult.success(userService.listUsers());
    }
}