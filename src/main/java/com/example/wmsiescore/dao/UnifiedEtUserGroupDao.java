package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtUserGroupQuery;
import com.example.wmsiescore.model.EtUserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组表统一DAO接口
 * 提供用户组表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtUserGroupDao {
    
    /**
     * 插入用户组记录（只插入非空字段）
     * @param etUserGroup 用户组实体对象
     * @return 影响的行数
     */
    int insertSelective(EtUserGroup etUserGroup);
    
    /**
     * 插入或更新用户组记录（使用ON DUPLICATE KEY UPDATE）
     * @param etUserGroup 用户组实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtUserGroup etUserGroup);
    
    /**
     * 根据条件查询用户组列表
     * @param query 查询条件对象
     * @return 用户组列表
     */
    List<EtUserGroup> selectByCondition(EtUserGroupQuery query);
    
    /**
     * 根据条件分页查询用户组列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 用户组列表
     */
    List<EtUserGroup> selectByConditionWithPage(EtUserGroupQuery query);
    
    /**
     * 根据条件统计用户组数量
     * @param query 查询条件对象
     * @return 用户组数量
     */
    int countByCondition(EtUserGroupQuery query);
    
    /**
     * 批量插入用户组记录
     * @param userGroups 用户组列表
     * @return 影响的行数
     */
    int batchInsert(@Param("userGroups") List<EtUserGroup> userGroups);
    
    /**
     * 批量删除用户组记录
     * @param ids 用户组ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新用户组记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtUserGroupQuery query);
    
    /**
     * 根据ID查询用户组记录
     * @param id 用户组ID
     * @return 用户组记录
     */
    EtUserGroup selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除用户组记录
     * @param id 用户组ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新用户组记录
     * @param etUserGroup 用户组实体对象
     * @return 影响的行数
     */
    int updateById(EtUserGroup etUserGroup);
    
    /**
     * 查询所有用户组记录
     * @return 用户组列表
     */
    List<EtUserGroup> selectAll();
    
    /**
     * 根据用户组名称查询用户组记录
     * @param groupName 用户组名称
     * @return 用户组记录
     */
    EtUserGroup selectByName(@Param("groupName") String groupName);
    
    /**
     * 根据创建人查询用户组记录
     * @param creator 创建人
     * @return 用户组列表
     */
    List<EtUserGroup> selectByCreator(@Param("creator") String creator);
    
    /**
     * 根据名称模糊查询用户组记录
     * @param groupName 用户组名称
     * @return 用户组列表
     */
    List<EtUserGroup> selectByNameLike(@Param("groupName") String groupName);
    
    /**
     * 根据用户名查询用户组记录
     * @param username 用户名
     * @return 用户组列表
     */
    List<EtUserGroup> getUserGroupsByUsername(@Param("username") String username);
}