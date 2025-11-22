package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtUserQuery;
import com.example.wmsiescore.model.EtUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表统一DAO接口
 * 提供用户表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedUserDao {
    
    /**
     * 插入用户记录（只插入非空字段）
     * @param etUser 用户实体对象
     * @return 影响的行数
     */
    int insertSelective(EtUser etUser);
    
    /**
     * 插入或更新用户记录（使用ON DUPLICATE KEY UPDATE）
     * @param etUser 用户实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtUser etUser);
    
    /**
     * 根据条件查询用户列表
     * @param query 查询条件对象
     * @return 用户列表
     */
    List<EtUser> selectByCondition(EtUserQuery query);
    
    /**
     * 根据条件分页查询用户列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 用户列表
     */
    List<EtUser> selectByConditionWithPage(EtUserQuery query);
    
    /**
     * 根据条件统计用户数量
     * @param query 查询条件对象
     * @return 用户数量
     */
    int countByCondition(EtUserQuery query);
    
    /**
     * 批量插入用户记录
     * @param users 用户列表
     * @return 影响的行数
     */
    int batchInsert(@Param("users") List<EtUser> users);
    
    /**
     * 批量删除用户记录
     * @param ids 用户ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新用户记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtUserQuery query);
    
    /**
     * 根据ID查询用户记录
     * @param id 用户ID
     * @return 用户记录
     */
    EtUser selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除用户记录
     * @param id 用户ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新用户记录
     * @param etUser 用户实体对象
     * @return 影响的行数
     */
    int updateById(EtUser etUser);
    
    /**
     * 查询所有用户记录
     * @return 用户列表
     */
    List<EtUser> selectAll();
    
    /**
     * 根据用户名查询用户记录
     * @param username 用户名
     * @return 用户记录
     */
    EtUser selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户记录
     * @param email 用户邮箱
     * @return 用户记录
     */
    EtUser selectByEmail(@Param("email") String email);
    
    /**
     * 根据手机号查询用户记录
     * @param phone 用户手机号
     * @return 用户记录
     */
    EtUser selectByPhone(@Param("phone") String phone);
    
    /**
     * 根据是否启用查询用户记录
     * @param enabled 是否启用
     * @return 用户列表
     */
    List<EtUser> selectByEnabled(@Param("enabled") Boolean enabled);
    
    /**
     * 根据是否外包查询用户记录
     * @param ifOutSource 是否外包
     * @return 用户列表
     */
    List<EtUser> selectByIfOutSource(@Param("ifOutSource") Boolean ifOutSource);
    
    /**
     * 根据领域ID查询用户记录
     * @param fieldId 领域ID
     * @return 用户列表
     */
    List<EtUser> selectByFieldId(@Param("fieldId") Long fieldId);
    
    /**
     * 根据公司查询用户记录
     * @param company 公司
     * @return 用户列表
     */
    List<EtUser> selectByCompany(@Param("company") String company);
    
    /**
     * 根据部门查询用户记录
     * @param department 部门
     * @return 用户列表
     */
    List<EtUser> selectByDepartment(@Param("department") String department);
    
    /**
     * 根据组名查询用户记录
     * @param groupname 组名
     * @return 用户列表
     */
    List<EtUser> selectByGroupname(@Param("groupname") String groupname);
    
    /**
     * 根据真实姓名模糊查询用户记录
     * @param truename 真实姓名
     * @return 用户列表
     */
    List<EtUser> selectByTruenameLike(@Param("truename") String truename);
    
    /**
     * 根据用户名模糊查询用户记录
     * @param username 用户名
     * @return 用户列表
     */
    List<EtUser> selectByUsernameLike(@Param("username") String username);
    
    /**
     * 更新用户管理员状态
     * @param userId 用户ID
     * @param isAdmin 是否管理员
     * @return 影响的行数
     */
    int updateAdminStatus(@Param("userId") Long userId, @Param("isAdmin") Boolean isAdmin);
    
    /**
     * 更新用户禁用状态
     * @param userId 用户ID
     * @param isDisabled 是否禁用
     * @return 影响的行数
     */
    int updateDisabledStatus(@Param("userId") Long userId, @Param("isDisabled") Boolean isDisabled);
    
    /**
     * 插入用户记录
     * @param user 用户实体对象
     * @return 影响的行数
     */
    int insertUser(EtUser user);
    
    /**
     * 删除用户记录
     * @param userId 用户ID
     * @return 影响的行数
     */
    int deleteUser(@Param("userId") Long userId);
    
    /**
     * 更新用户记录
     * @param user 用户实体对象
     * @return 影响的行数
     */
    int updateUser(EtUser user);
    
    /**
     * 获取用户记录
     * @param userId 用户ID
     * @return 用户记录
     */
    EtUser getUser(@Param("userId") Long userId);
    
    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    List<EtUser> listUsers();
    
    /**
     * 分页获取用户列表
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 用户列表
     */
    List<EtUser> listUsersByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
}