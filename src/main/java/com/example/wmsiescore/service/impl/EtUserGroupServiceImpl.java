package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtUserGroupDao;
import com.example.wmsiescore.dao.UnifiedEtUserGroupMemberDao;
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
    private UnifiedEtUserGroupDao unifiedEtUserGroupDao;
    
    @Autowired
    private UnifiedEtUserGroupMemberDao unifiedEtUserGroupMemberDao;

    @Override
    @Transactional
    public Long createUserGroup(EtUserGroup userGroup) {
        userGroup.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userGroup.setStatus("active");
        unifiedEtUserGroupDao.insertSelective(userGroup);
        return userGroup.getId();
    }

    @Override
    @Transactional
    public Boolean updateUserGroup(EtUserGroup userGroup) {
        userGroup.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtUserGroupDao.updateById(userGroup) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteUserGroup(Long id) {
        // 先删除分组成员
        List<EtUserGroupMember> members = unifiedEtUserGroupMemberDao.selectByUserGroupId(id);
        for (EtUserGroupMember member : members) {
            unifiedEtUserGroupMemberDao.deleteById(member.getId());
        }
        // 再删除分组
        return unifiedEtUserGroupDao.deleteById(id) > 0;
    }

    @Override
    public EtUserGroup getUserGroupById(Long id) {
        return unifiedEtUserGroupDao.selectById(id);
    }

    @Override
    public List<EtUserGroup> listAllUserGroups() {
        return unifiedEtUserGroupDao.selectAll();
    }

    @Override
    @Transactional
    public Boolean addUserToGroup(Long groupId, Long userId, String userName, String userAccount) {
        // 检查用户是否已在分组中
        EtUserGroupMember existingMember = unifiedEtUserGroupMemberDao.selectByUserGroupIdAndUserId(groupId, userId);
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
        
        unifiedEtUserGroupMemberDao.insertSelective(member);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeUserFromGroup(Long groupId, Long userId) {
        EtUserGroupMember member = unifiedEtUserGroupMemberDao.selectByUserGroupIdAndUserId(groupId, userId);
        if (member != null) {
            return unifiedEtUserGroupMemberDao.deleteById(member.getId()) > 0;
        }
        return false;
    }

    @Override
    public List<EtUserGroupMember> getGroupMembers(Long groupId) {
        return unifiedEtUserGroupMemberDao.selectByUserGroupId(groupId);
    }

    @Override
    public List<EtUserGroupMember> getUserGroups(Long userId) {
        return unifiedEtUserGroupMemberDao.selectByUserId(userId);
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
        EtUserGroup userGroup = unifiedEtUserGroupDao.selectById(id);
        if (userGroup != null) {
            userGroup.setStatus("active");
            return unifiedEtUserGroupDao.updateById(userGroup) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deactivateUserGroup(Long id) {
        EtUserGroup userGroup = unifiedEtUserGroupDao.selectById(id);
        if (userGroup != null) {
            userGroup.setStatus("inactive");
            return unifiedEtUserGroupDao.updateById(userGroup) > 0;
        }
        return false;
    }
}