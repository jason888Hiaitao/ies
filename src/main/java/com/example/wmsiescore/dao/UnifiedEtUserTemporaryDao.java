package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtUserTemporaryQuery;
import com.example.wmsiescore.model.EtUserTemporary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 临时用户表统一DAO接口
 * 提供临时用户表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtUserTemporaryDao {
    
    /**
     * 插入临时用户记录（只插入非空字段）
     * @param etUserTemporary 临时用户实体对象
     * @return 影响的行数
     */
    int insertSelective(EtUserTemporary etUserTemporary);
    
    /**
     * 插入或更新临时用户记录（使用ON DUPLICATE KEY UPDATE）
     * @param etUserTemporary 临时用户实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtUserTemporary etUserTemporary);
    
    /**
     * 根据条件查询临时用户列表
     * @param query 查询条件对象
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByCondition(EtUserTemporaryQuery query);
    
    /**
     * 根据条件分页查询临时用户列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByConditionWithPage(EtUserTemporaryQuery query);
    
    /**
     * 根据条件统计临时用户数量
     * @param query 查询条件对象
     * @return 临时用户数量
     */
    int countByCondition(EtUserTemporaryQuery query);
    
    /**
     * 批量插入临时用户记录
     * @param userTemporaries 临时用户列表
     * @return 影响的行数
     */
    int batchInsert(@Param("userTemporaries") List<EtUserTemporary> userTemporaries);
    
    /**
     * 批量删除临时用户记录
     * @param ids 临时用户ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新临时用户记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtUserTemporaryQuery query);
    
    /**
     * 根据ID查询临时用户记录
     * @param id 临时用户ID
     * @return 临时用户记录
     */
    EtUserTemporary selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除临时用户记录
     * @param id 临时用户ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新临时用户记录
     * @param etUserTemporary 临时用户实体对象
     * @return 影响的行数
     */
    int updateById(EtUserTemporary etUserTemporary);
    
    /**
     * 查询所有临时用户记录
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectAll();
    
    /**
     * 根据用户名查询临时用户记录
     * @param username 用户名
     * @return 临时用户记录
     */
    EtUserTemporary selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询临时用户记录
     * @param email 用户邮箱
     * @return 临时用户记录
     */
    EtUserTemporary selectByEmail(@Param("email") String email);
    
    /**
     * 根据手机号查询临时用户记录
     * @param phone 用户手机号
     * @return 临时用户记录
     */
    EtUserTemporary selectByPhone(@Param("phone") String phone);
    
    /**
     * 根据是否启用查询临时用户记录
     * @param enabled 是否启用
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByEnabled(@Param("enabled") Boolean enabled);
    
    /**
     * 根据是否外包查询临时用户记录
     * @param ifOutSource 是否外包
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByIfOutSource(@Param("ifOutSource") Boolean ifOutSource);
    
    /**
     * 根据领域ID查询临时用户记录
     * @param fieldId 领域ID
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByFieldId(@Param("fieldId") Long fieldId);
    
    /**
     * 根据公司查询临时用户记录
     * @param company 公司
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByCompany(@Param("company") String company);
    
    /**
     * 根据部门查询临时用户记录
     * @param department 部门
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByDepartment(@Param("department") String department);
    
    /**
     * 根据组名查询临时用户记录
     * @param groupname 组名
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByGroupname(@Param("groupname") String groupname);
    
    /**
     * 根据真实姓名模糊查询临时用户记录
     * @param truename 真实姓名
     * @return 临时用户列表
     */
    List<EtUserTemporary> selectByTruenameLike(@Param("truename") String truename);
}