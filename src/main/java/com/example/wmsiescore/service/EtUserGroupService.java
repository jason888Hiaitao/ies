package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtUserGroup;
import com.example.wmsiescore.model.EtUserGroupMember;

import java.util.List;

public interface EtUserGroupService {
    /**
     * 创建分组
     */
    Long createUserGroup(EtUserGroup userGroup);

    /**
     * 更新分组
     */
    Boolean updateUserGroup(EtUserGroup userGroup);

    /**
     * 删除分组
     */
    Boolean deleteUserGroup(Long id);

    /**
     * 获取分组详情
     */
    EtUserGroup getUserGroupById(Long id);

    /**
     * 获取所有分组列表
     */
    List<EtUserGroup> listAllUserGroups();

    /**
     * 添加用户到分组
     */
    Boolean addUserToGroup(Long groupId, Long userId, String userName, String userAccount);

    /**
     * 从分组中移除用户
     */
    Boolean removeUserFromGroup(Long groupId, Long userId);

    /**
     * 获取分组成员列表
     */
    List<EtUserGroupMember> getGroupMembers(Long groupId);

    /**
     * 获取用户所属分组列表
     */
    List<EtUserGroupMember> getUserGroups(Long userId);

    /**
     * 批量添加用户到分组
     */
    Boolean batchAddUsersToGroup(Long groupId, List<Long> userIds);

    /**
     * 批量移除用户从分组
     */
    Boolean batchRemoveUsersFromGroup(Long groupId, List<Long> userIds);

    /**
     * 激活分组
     */
    Boolean activateUserGroup(Long id);

    /**
     * 停用分组
     */
    Boolean deactivateUserGroup(Long id);
}