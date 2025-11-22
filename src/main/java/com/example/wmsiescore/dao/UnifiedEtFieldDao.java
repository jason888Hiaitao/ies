package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtFieldQuery;
import com.example.wmsiescore.model.EtField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 领域表统一DAO接口
 * 提供领域表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtFieldDao {
    
    /**
     * 插入领域记录（只插入非空字段）
     * @param etField 领域实体对象
     * @return 影响的行数
     */
    int insertSelective(EtField etField);
    
    /**
     * 插入或更新领域记录（使用ON DUPLICATE KEY UPDATE）
     * @param etField 领域实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtField etField);
    
    /**
     * 根据条件查询领域列表
     * @param query 查询条件对象
     * @return 领域列表
     */
    List<EtField> selectByCondition(EtFieldQuery query);
    
    /**
     * 根据条件分页查询领域列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 领域列表
     */
    List<EtField> selectByConditionWithPage(EtFieldQuery query);
    
    /**
     * 根据条件统计领域数量
     * @param query 查询条件对象
     * @return 领域数量
     */
    int countByCondition(EtFieldQuery query);
    
    /**
     * 批量插入领域记录
     * @param fields 领域列表
     * @return 影响的行数
     */
    int batchInsert(@Param("fields") List<EtField> fields);
    
    /**
     * 批量删除领域记录
     * @param fieldIds 领域ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("fieldIds") List<Integer> fieldIds);
    
    /**
     * 根据条件更新领域记录（只更新非空字段）
     * @param query 查询条件对象（必须包含fieldId）
     * @return 影响的行数
     */
    int updateByCondition(EtFieldQuery query);
    
    /**
     * 根据ID查询领域记录
     * @param fieldId 领域ID
     * @return 领域记录
     */
    EtField selectById(@Param("fieldId") Integer fieldId);
    
    /**
     * 根据ID删除领域记录
     * @param fieldId 领域ID
     * @return 影响的行数
     */
    int deleteById(@Param("fieldId") Integer fieldId);
    
    /**
     * 根据ID更新领域记录
     * @param etField 领域实体对象
     * @return 影响的行数
     */
    int updateById(EtField etField);
    
    /**
     * 查询所有领域记录
     * @return 领域列表
     */
    List<EtField> selectAll();
    
    /**
     * 根据领域名称查询领域记录
     * @param fieldName 领域名称
     * @return 领域记录
     */
    EtField selectByName(@Param("fieldName") String fieldName);
    
    /**
     * 根据状态查询领域记录
     * @param state 状态
     * @return 领域列表
     */
    List<EtField> selectByState(@Param("state") Integer state);
    
    /**
     * 根据名称模糊查询领域记录
     * @param fieldName 领域名称
     * @return 领域列表
     */
    List<EtField> selectByNameLike(@Param("fieldName") String fieldName);
}