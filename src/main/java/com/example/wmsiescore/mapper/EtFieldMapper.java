package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题库Mapper接口
 */
@Mapper
public interface EtFieldMapper  {
    
    /**
     * 根据条件查询列表
     */
    List<EtField> selectList(@Param("field") EtField field);
    
    /**
     * 根据ID查询
     */
    EtField selectById(@Param("fieldId") Integer fieldId);
    
    /**
     * 插入记录
     */
    int insert(EtField field);
    
    /**
     * 根据ID更新
     */
    int updateById(EtField field);
    
    /**
     * 根据ID删除
     */
    int deleteById(@Param("fieldId") Integer fieldId);
    
    /**
     * 批量删除
     */
    int deleteBatchIds(@Param("ids") List<Integer> ids);
    
    /**
     * 查询所有记录
     */
    List<EtField> selectAll();
}