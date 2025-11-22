package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtUserGroupMemberQuery;
import com.example.wmsiescore.model.EtUserGroupMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组成员表统一DAO接口
 * 提供用户组成员表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtUserGroupMemberDao {
    
    /**
     * 插入用户组成员记录（只插入非空字段）
     * @param etUserGroupMember 用户组成员实体对象
     * @return 影响的行数
     */
    int insertSelective(EtUserGroupMember etUserGroupMember);
    
    /**
     * 插入或更新用户组成员记录（使用ON DUPLICATE KEY UPDATE）
     * @param etUserGroupMember 用户组成员实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtUserGroupMember etUserGroupMember);
    
    /**
     * 根据条件查询用户组成员列表
     * @param query 查询条件对象
     * @return 用户组成员列表
     */
    List<EtUserGroupMember> selectByCondition(EtUserGroupMemberQuery query);
    
    /**
     * 根据条件分页查询用户组成员列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 用户组成员列表
     */
    List<EtUserGroupMember> selectByConditionWithPage(EtUserGroupMemberQuery query);
    
    /**
     * 根据条件统计用户组成员数量
     * @param query 查询条件对象
     * @return 用户组成员数量
     */
    int countByCondition(EtUserGroupMemberQuery query);
    
    /**
     * 批量插入用户组成员记录
     * @param userGroupMembers 用户组成员列表
     * @return 影响的行数
     */
    int batchInsert(@Param("userGroupMembers") List<EtUserGroupMember> userGroupMembers);
    
    /**
     * 批量删除用户组成员记录
     * @param ids 用户组成员ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新用户组成员记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtUserGroupMemberQuery query);
    
    /**
     * 根据ID查询用户组成员记录
     * @param id 用户组成员ID
     * @return 用户组成员记录
     */
    EtUserGroupMember selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除用户组成员记录
     * @param id 用户组成员ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新用户组成员记录
     * @param etUserGroupMember 用户组成员实体对象
     * @return 影响的行数
     */
    int updateById(EtUserGroupMember etUserGroupMember);
    
    /**
     * 查询所有用户组成员记录
     * @return 用户组成员列表
     */
    List<EtUserGroupMember> selectAll();
    
    /**
     * 根据用户组ID查询用户组成员记录
     * @param userGroupId 用户组ID
     * @return 用户组成员列表
     */
    List<EtUserGroupMember> selectByUserGroupId(@Param("userGroupId") Long userGroupId);
    
    /**
     * 根据用户ID查询用户组成员记录
     * @param userId 用户ID
     * @return 用户组成员列表
     */
    List<EtUserGroupMember> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色查询用户组成员记录
     * @param role 角色
     * @return 用户组成员列表
     */
    List<EtUserGroupMember> selectByRole(@Param("role") String role);
    
    /**
     * 根据用户组ID和用户ID查询用户组成员记录
     * @param userGroupId 用户组ID
     * @param userId 用户ID
     * @return 用户组成员记录
     */
    EtUserGroupMember selectByUserGroupIdAndUserId(
        @Param("userGroupId") Long userGroupId,
        @Param("userId") Long userId
    );
}