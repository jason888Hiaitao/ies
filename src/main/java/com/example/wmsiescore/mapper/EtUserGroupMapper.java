package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtUserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EtUserGroupMapper {
    void insertUserGroup(EtUserGroup userGroup);

    int updateUserGroup(EtUserGroup userGroup);

    int deleteUserGroup(@Param("id") Long id);

    EtUserGroup getUserGroupById(@Param("id") Long id);

    List<EtUserGroup> listUserGroupsByCreator(@Param("createdBy") Long createdBy);

    List<EtUserGroup> listAllUserGroups();

    int updateUserGroupStatus(@Param("id") Long id, @Param("status") String status);

    // 根据用户名查询群组列表
    List<EtUserGroup> getUserGroupsByUsername(@Param("username") String username);
}