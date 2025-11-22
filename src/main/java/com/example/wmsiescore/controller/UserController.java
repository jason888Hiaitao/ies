package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUser;
import com.example.wmsiescore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理接口", description = "用户的增删改查和权限管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "设置管理员权限", description = "设置用户是否为管理员")
    @PostMapping("/setAdmin")
    public ResponseResult<Boolean> setAdmin(@Parameter(description = "用户ID", required = true) @RequestParam Long userId, 
                                            @Parameter(description = "是否为管理员", required = true) @RequestParam Boolean isAdmin) {
        return ResponseResult.success(userService.setAdmin(userId, isAdmin));
    }

    @Operation(summary = "设置用户禁用状态", description = "设置用户是否被禁用")
    @PostMapping("/setDisabled")
    public ResponseResult<Boolean> setDisabled(@Parameter(description = "用户ID", required = true) @RequestParam Long userId, 
                                                @Parameter(description = "是否禁用", required = true) @RequestParam Boolean isDisabled) {
        return ResponseResult.success(userService.setDisabled(userId, isDisabled));
    }

    @Operation(summary = "添加用户", description = "创建新的用户")
    @PostMapping("/add")
    public ResponseResult<Long> addUser(@Parameter(description = "用户信息", required = true) @RequestBody EtUser user) {
        return ResponseResult.success(userService.addUser(user));
    }

    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteUser(@Parameter(description = "用户ID", required = true) @RequestBody Long userId) {
        return ResponseResult.success(userService.deleteUser(userId));
    }

    @Operation(summary = "更新用户", description = "更新用户信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateUser(@Parameter(description = "用户信息", required = true) @RequestBody EtUser user) {
        return ResponseResult.success(userService.updateUser(user));
    }

    @Operation(summary = "获取用户", description = "根据ID获取用户信息")
    @PostMapping("/getById")
    public ResponseResult<EtUser> getUser(@Parameter(description = "用户ID", required = true) @RequestBody Long userId) {
        return ResponseResult.success(userService.getUser(userId));
    }

    @Operation(summary = "获取用户列表", description = "获取所有用户列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUser>> listUsers() {
        return ResponseResult.success(userService.listUsers());
    }
}
