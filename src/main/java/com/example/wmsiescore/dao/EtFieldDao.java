package com.example.wmsiescore.dao;

import com.example.wmsiescore.model.EtField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 题库领域表DAO接口
 */
@Mapper
public interface EtFieldDao {
    
    /**
     * 插入领域记录
     */
    int insert(EtField etField);
    
    /**
     * 根据ID删除领域记录
     */
    int deleteById(@Param("fieldId") Integer fieldId);
    
    /**
     * 更新领域记录
     */
    int updateById(EtField etField);
    
    /**
     * 根据ID查询领域记录
     */
    EtField selectById(@Param("fieldId") Integer fieldId);
    
    /**
     * 查询所有领域记录
     */
    List<EtField> selectAll();
    
    /**
     * 根据状态查询领域记录
     */
    List<EtField> selectByState(@Param("state") BigDecimal state);
    
    /**
     * 根据名称模糊查询领域记录
     */
    List<EtField> selectByNameLike(@Param("fieldName") String fieldName);
    
    /**
     * 批量删除领域记录
     */
    int deleteByIds(@Param("fieldIds") List<Integer> fieldIds);
    
    /**
     * 统计记录总数
     */
    int countAll();
    
    /**
     * 根据状态统计记录数
     */
    int countByState(@Param("state") BigDecimal state);
    
    /**
     * 根据ID列表查询领域记录
     */
    List<EtField> selectByIds(@Param("fieldIds") List<Integer> fieldIds);
}