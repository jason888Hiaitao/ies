package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtUserGroupMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EtUserGroupMemberMapper {
    void insertUserGroupMember(EtUserGroupMember member);

    int updateUserGroupMember(EtUserGroupMember member);

    int deleteUserGroupMember(@Param("id") Long id);

    int deleteUserGroupMemberByUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);

    EtUserGroupMember getUserGroupMember(@Param("groupId") Long groupId, @Param("userId") Long userId);

    List<EtUserGroupMember> listMembersByGroupId(@Param("groupId") Long groupId);

    List<EtUserGroupMember> listGroupsByUserId(@Param("userId") Long userId);

    int updateUserGroupMemberStatus(@Param("id") Long id, @Param("status") String status);
}