package com.example.wmsiescore.dao;

import com.example.wmsiescore.model.EtKnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 知识点表DAO接口
 */
@Mapper
public interface EtKnowledgePointDao {
    
    /**
     * 插入知识点记录
     */
    int insert(EtKnowledgePoint etKnowledgePoint);
    
    /**
     * 根据ID删除知识点记录
     */
    int deleteById(@Param("pointId") Integer pointId);
    
    /**
     * 更新知识点记录
     */
    int updateById(EtKnowledgePoint etKnowledgePoint);
    
    /**
     * 根据ID查询知识点记录
     */
    EtKnowledgePoint selectById(@Param("pointId") Integer pointId);
    
    /**
     * 查询所有知识点记录
     */
    List<EtKnowledgePoint> selectAll();
    
    /**
     * 根据领域ID查询知识点记录
     */
    List<EtKnowledgePoint> selectByFieldId(@Param("fieldId") Integer fieldId);
    
    /**
     * 根据状态查询知识点记录
     */
    List<EtKnowledgePoint> selectByState(@Param("state") BigDecimal state);
    
    /**
     * 根据名称模糊查询知识点记录
     */
    List<EtKnowledgePoint> selectByNameLike(@Param("pointName") String pointName);
    
    /**
     * 根据领域ID和状态查询知识点记录
     */
    List<EtKnowledgePoint> selectByFieldIdAndState(@Param("fieldId") Integer fieldId, @Param("state") BigDecimal state);
    
    /**
     * 批量删除知识点记录
     */
    int deleteByIds(@Param("pointIds") List<Integer> pointIds);
    
    /**
     * 根据领域ID删除知识点记录
     */
    int deleteByFieldId(@Param("fieldId") Integer fieldId);
    
    /**
     * 统计记录总数
     */
    int countAll();
    
    /**
     * 根据领域ID统计记录数
     */
    int countByFieldId(@Param("fieldId") Integer fieldId);
    
    /**
     * 根据状态统计记录数
     */
    int countByState(@Param("state") BigDecimal state);
    
    /**
     * 根据ID列表查询知识点记录
     */
    List<EtKnowledgePoint> selectByIds(@Param("pointIds") List<Integer> pointIds);
}