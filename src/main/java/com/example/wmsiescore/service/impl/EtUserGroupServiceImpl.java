package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.EtUserGroupMapper;
import com.example.wmsiescore.mapper.EtUserGroupMemberMapper;
import com.example.wmsiescore.model.EtUserGroup;
import com.example.wmsiescore.model.EtUserGroupMember;
import com.example.wmsiescore.service.EtUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EtUserGroupServiceImpl implements EtUserGroupService {
    @Autowired
    private EtUserGroupMapper etUserGroupMapper;
    
    @Autowired
    private EtUserGroupMemberMapper etUserGroupMemberMapper;

    @Override
    @Transactional
    public Long createUserGroup(EtUserGroup userGroup) {
        userGroup.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userGroup.setStatus("active");
        etUserGroupMapper.insertUserGroup(userGroup);
        return userGroup.getId();
    }

    @Override
    @Transactional
    public Boolean updateUserGroup(EtUserGroup userGroup) {
        userGroup.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etUserGroupMapper.updateUserGroup(userGroup) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteUserGroup(Long id) {
        // 先删除分组成员
        List<EtUserGroupMember> members = etUserGroupMemberMapper.listMembersByGroupId(id);
        for (EtUserGroupMember member : members) {
            etUserGroupMemberMapper.deleteUserGroupMember(member.getId());
        }
        // 再删除分组
        return etUserGroupMapper.deleteUserGroup(id) > 0;
    }

    @Override
    public EtUserGroup getUserGroupById(Long id) {
        return etUserGroupMapper.getUserGroupById(id);
    }

    @Override
    public List<EtUserGroup> listAllUserGroups() {
        return etUserGroupMapper.listAllUserGroups();
    }

    @Override
    @Transactional
    public Boolean addUserToGroup(Long groupId, Long userId, String userName, String userAccount) {
        // 检查用户是否已在分组中
        EtUserGroupMember existingMember = etUserGroupMemberMapper.getUserGroupMember(groupId, userId);
        if (existingMember != null) {
            return false; // 用户已在分组中
        }
        
        EtUserGroupMember member = new EtUserGroupMember();
        member.setGroupId(groupId);
        member.setUserId(userId);
        member.setUserName(userName);
        member.setUserAccount(userAccount);
        member.setStatus("active");
        member.setCreateTime(new Timestamp(System.currentTimeMillis()));
        
        etUserGroupMemberMapper.insertUserGroupMember(member);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeUserFromGroup(Long groupId, Long userId) {
        return etUserGroupMemberMapper.deleteUserGroupMemberByUserId(groupId, userId) > 0;
    }

    @Override
    public List<EtUserGroupMember> getGroupMembers(Long groupId) {
        return etUserGroupMemberMapper.listMembersByGroupId(groupId);
    }

    @Override
    public List<EtUserGroupMember> getUserGroups(Long userId) {
        return etUserGroupMemberMapper.listGroupsByUserId(userId);
    }

    @Override
    @Transactional
    public Boolean batchAddUsersToGroup(Long groupId, List<Long> userIds) {
        // 这里简化实现，实际应该先查询用户信息再批量添加
        for (Long userId : userIds) {
            addUserToGroup(groupId, userId, "用户" + userId, "account" + userId);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean batchRemoveUsersFromGroup(Long groupId, List<Long> userIds) {
        for (Long userId : userIds) {
            removeUserFromGroup(groupId, userId);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean activateUserGroup(Long id) {
        return etUserGroupMapper.updateUserGroupStatus(id, "active") > 0;
    }

    @Override
    @Transactional
    public Boolean deactivateUserGroup(Long id) {
        return etUserGroupMapper.updateUserGroupStatus(id, "inactive") > 0;
    }
}