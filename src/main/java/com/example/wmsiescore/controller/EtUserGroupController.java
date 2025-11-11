package com.example.wmsiescore.controller;

import com.example.wmsiescore.common.ResponseResult;
import com.example.wmsiescore.model.EtUserGroup;
import com.example.wmsiescore.model.EtUserGroupMember;
import com.example.wmsiescore.service.EtUserGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userGroup")
//@Api(tags = "用户群组管理接口", description = "用户群组的创建、编辑、删除和成员管理")
public class EtUserGroupController {
    @Autowired
    private EtUserGroupService etUserGroupService;

    @ApiOperation(value = "创建分组", notes = "管理员创建新的用户分组")
    @PostMapping("/create")
    public ResponseResult<Long> createUserGroup(@ApiParam(value = "分组信息", required = true) @RequestBody EtUserGroup userGroup) {
        return ResponseResult.success(etUserGroupService.createUserGroup(userGroup));
    }

    @ApiOperation(value = "更新分组", notes = "更新已存在的分组信息")
    @PostMapping("/update")
    public ResponseResult<Boolean> updateUserGroup(@ApiParam(value = "分组信息", required = true) @RequestBody EtUserGroup userGroup) {
        return ResponseResult.success(etUserGroupService.updateUserGroup(userGroup));
    }

    @ApiOperation(value = "删除分组", notes = "根据ID删除指定的分组")
    @PostMapping("/delete")
    public ResponseResult<Boolean> deleteUserGroup(@ApiParam(value = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.deleteUserGroup(id));
    }

    @ApiOperation(value = "获取分组详情", notes = "根据ID获取分组的详细信息")
    @PostMapping("/getById")
    public ResponseResult<EtUserGroup> getUserGroupById(@ApiParam(value = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.getUserGroupById(id));
    }

    @ApiOperation(value = "获取所有分组列表", notes = "获取系统中所有分组的列表")
    @PostMapping("/list")
    public ResponseResult<List<EtUserGroup>> listAllUserGroups() {
        return ResponseResult.success(etUserGroupService.listAllUserGroups());
    }

    @ApiOperation(value = "添加用户到分组", notes = "将指定用户添加到分组中")
    @PostMapping("/addUserToGroup")
    public ResponseResult<Boolean> addUserToGroup(@ApiParam(value = "分组ID", required = true) @RequestParam Long groupId, 
                                                 @ApiParam(value = "用户ID", required = true) @RequestParam Long userId,
                                                 @ApiParam(value = "用户姓名", required = true) @RequestParam String userName,
                                                 @ApiParam(value = "用户账号", required = true) @RequestParam String userAccount) {
        return ResponseResult.success(etUserGroupService.addUserToGroup(groupId, userId, userName, userAccount));
    }

    @ApiOperation(value = "从分组中移除用户", notes = "将指定用户从分组中移除")
    @PostMapping("/removeUserFromGroup")
    public ResponseResult<Boolean> removeUserFromGroup(@ApiParam(value = "分组ID", required = true) @RequestParam Long groupId, 
                                                      @ApiParam(value = "用户ID", required = true) @RequestParam Long userId) {
        return ResponseResult.success(etUserGroupService.removeUserFromGroup(groupId, userId));
    }

    @ApiOperation(value = "获取分组成员列表", notes = "获取指定分组的所有成员")
    @PostMapping("/getGroupMembers")
    public ResponseResult<List<EtUserGroupMember>> getGroupMembers(@ApiParam(value = "分组ID", required = true) @RequestBody Long groupId) {
        return ResponseResult.success(etUserGroupService.getGroupMembers(groupId));
    }

    @ApiOperation(value = "获取用户所属分组列表", notes = "获取指定用户所属的所有分组")
    @PostMapping("/getUserGroups")
    public ResponseResult<List<EtUserGroupMember>> getUserGroups(@ApiParam(value = "用户ID", required = true) @RequestBody Long userId) {
        return ResponseResult.success(etUserGroupService.getUserGroups(userId));
    }

    @ApiOperation(value = "批量添加用户到分组", notes = "将多个用户批量添加到分组中")
    @PostMapping("/batchAddUsersToGroup")
    public ResponseResult<Boolean> batchAddUsersToGroup(@ApiParam(value = "分组ID", required = true) @RequestParam Long groupId, 
                                                        @ApiParam(value = "用户ID列表", required = true) @RequestBody List<Long> userIds) {
        return ResponseResult.success(etUserGroupService.batchAddUsersToGroup(groupId, userIds));
    }

    @ApiOperation(value = "批量移除用户从分组", notes = "将多个用户批量从分组中移除")
    @PostMapping("/batchRemoveUsersFromGroup")
    public ResponseResult<Boolean> batchRemoveUsersFromGroup(@ApiParam(value = "分组ID", required = true) @RequestParam Long groupId, 
                                                           @ApiParam(value = "用户ID列表", required = true) @RequestBody List<Long> userIds) {
        return ResponseResult.success(etUserGroupService.batchRemoveUsersFromGroup(groupId, userIds));
    }

    @ApiOperation(value = "激活分组", notes = "将分组状态设置为激活")
    @PostMapping("/activate")
    public ResponseResult<Boolean> activateUserGroup(@ApiParam(value = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.activateUserGroup(id));
    }

    @ApiOperation(value = "停用分组", notes = "将分组状态设置为停用")
    @PostMapping("/deactivate")
    public ResponseResult<Boolean> deactivateUserGroup(@ApiParam(value = "分组ID", required = true) @RequestBody Long id) {
        return ResponseResult.success(etUserGroupService.deactivateUserGroup(id));
    }
}