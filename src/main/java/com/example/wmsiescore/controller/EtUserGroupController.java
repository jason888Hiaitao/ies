package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUserGroup;
import com.example.wmsiescore.model.EtUserGroupMember;
import com.example.wmsiescore.service.EtUserGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userGroup")
//@Tag(name = "用户群组管理接口", description = "用户群组的创建、编辑、删除和成员管理")
public class EtUserGroupController {
    @Autowired
    private EtUserGroupService etUserGroupService;

    @Operation(summary = "创建分组", description = "管理员创建新的用户分组")
    @PostMapping("/create")
    public ResponseResult<Long> createUserGroup(@Parameter(description = "分组信息", required = true) @RequestBody EtUserGroup userGroup) {
        return ResponseResult.success(etUserGroupService.createUserGroup(userGroup));
    }

    @Operation(summary = "更新分组", description = "更新已存在的分组信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateUserGroup(@Parameter(description = "分组信息", required = true) @RequestBody EtUserGroup userGroup) {
        return ResponseResult.success(etUserGroupService.updateUserGroup(userGroup));
    }

    @Operation(summary = "删除分组", description = "根据ID删除指定的分组")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteUserGroup(@Parameter(description = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.deleteUserGroup(id));
    }

    @Operation(summary = "获取分组详情", description = "根据ID获取分组的详细信息")
    @PostMapping("/getById")
    public ResponseResult<EtUserGroup> getUserGroupById(@Parameter(description = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.getUserGroupById(id));
    }

    @Operation(summary = "获取所有分组列表", description = "获取系统中所有分组的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUserGroup>> listAllUserGroups() {
        return ResponseResult.success(etUserGroupService.listAllUserGroups());
    }

    @Operation(summary = "添加用户到分组", description = "将指定用户添加到分组中")
    @PostMapping("/addUserToGroup")
    public ResponseResult<Boolean> addUserToGroup(@Parameter(description = "分组ID", required = true) @RequestParam Long groupId, 
                                                 @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
                                                 @Parameter(description = "用户姓名", required = true) @RequestParam String userName,
                                                 @Parameter(description = "用户账号", required = true) @RequestParam String userAccount) {
        return ResponseResult.success(etUserGroupService.addUserToGroup(groupId, userId, userName, userAccount));
    }

    @Operation(summary = "从分组中移除用户", description = "将指定用户从分组中移除")
    @PostMapping("/removeUserFromGroup")
    public ResponseResult<Boolean> removeUserFromGroup(@Parameter(description = "分组ID", required = true) @RequestParam Long groupId, 
                                                      @Parameter(description = "用户ID", required = true) @RequestParam Long userId) {
        return ResponseResult.success(etUserGroupService.removeUserFromGroup(groupId, userId));
    }

    @Operation(summary = "获取分组成员列表", description = "获取指定分组的所有成员")
    @PostMapping("/getGroupMembers")
    public ResponseResult<List<EtUserGroupMember>> getGroupMembers(@Parameter(description = "分组ID", required = true) @RequestBody Long groupId) {
        return ResponseResult.success(etUserGroupService.getGroupMembers(groupId));
    }

    @Operation(summary = "获取用户所属分组列表", description = "获取指定用户所属的所有分组")
    @PostMapping("/getUserGroups")
    public ResponseResult<List<EtUserGroupMember>> getUserGroups(@Parameter(description = "用户ID", required = true) @RequestBody Long userId) {
        return ResponseResult.success(etUserGroupService.getUserGroups(userId));
    }

    @Operation(summary = "批量添加用户到分组", description = "将多个用户批量添加到分组中")
    @PostMapping("/batchAddUsersToGroup")
    public ResponseResult<Boolean> batchAddUsersToGroup(@Parameter(description = "分组ID", required = true) @RequestParam Long groupId, 
                                                        @Parameter(description = "用户ID列表", required = true) @RequestBody List<Long> userIds) {
        return ResponseResult.success(etUserGroupService.batchAddUsersToGroup(groupId, userIds));
    }

    @Operation(summary = "批量移除用户从分组", description = "将多个用户批量从分组中移除")
    @PostMapping("/batchRemoveUsersFromGroup")
    public ResponseResult<Boolean> batchRemoveUsersFromGroup(@Parameter(description = "分组ID", required = true) @RequestParam Long groupId, 
                                                           @Parameter(description = "用户ID列表", required = true) @RequestBody List<Long> userIds) {
        return ResponseResult.success(etUserGroupService.batchRemoveUsersFromGroup(groupId, userIds));
    }

    @Operation(summary = "激活分组", description = "将分组状态设置为激活")
    @PostMapping("/activate")
    public ResponseResult<Boolean> activateUserGroup(@Parameter(description = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.activateUserGroup(id));
    }

    @Operation(summary = "停用分组", description = "将分组状态设置为停用")
    @PostMapping("/deactivate")
    public ResponseResult<Boolean> deactivateUserGroup(@Parameter(description = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.deactivateUserGroup(id));
    }
}
