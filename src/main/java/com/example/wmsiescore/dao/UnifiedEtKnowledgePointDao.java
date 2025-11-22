package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtKnowledgePointQuery;
import com.example.wmsiescore.model.EtKnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识点表统一DAO接口
 * 提供知识点表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtKnowledgePointDao {
    
    /**
     * 插入知识点记录（只插入非空字段）
     * @param etKnowledgePoint 知识点实体对象
     * @return 影响的行数
     */
    int insertSelective(EtKnowledgePoint etKnowledgePoint);
    
    /**
     * 插入或更新知识点记录（使用ON DUPLICATE KEY UPDATE）
     * @param etKnowledgePoint 知识点实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtKnowledgePoint etKnowledgePoint);
    
    /**
     * 根据条件查询知识点列表
     * @param query 查询条件对象
     * @return 知识点列表
     */
    List<EtKnowledgePoint> selectByCondition(EtKnowledgePointQuery query);
    
    /**
     * 根据条件分页查询知识点列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 知识点列表
     */
    List<EtKnowledgePoint> selectByConditionWithPage(EtKnowledgePointQuery query);
    
    /**
     * 根据条件统计知识点数量
     * @param query 查询条件对象
     * @return 知识点数量
     */
    int countByCondition(EtKnowledgePointQuery query);
    
    /**
     * 批量插入知识点记录
     * @param knowledgePoints 知识点列表
     * @return 影响的行数
     */
    int batchInsert(@Param("knowledgePoints") List<EtKnowledgePoint> knowledgePoints);
    
    /**
     * 批量删除知识点记录
     * @param pointIds 知识点ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("pointIds") List<Integer> pointIds);
    
    /**
     * 根据条件更新知识点记录（只更新非空字段）
     * @param query 查询条件对象（必须包含pointId）
     * @return 影响的行数
     */
    int updateByCondition(EtKnowledgePointQuery query);
    
    /**
     * 根据ID查询知识点记录
     * @param pointId 知识点ID
     * @return 知识点记录
     */
    EtKnowledgePoint selectById(@Param("pointId") Integer pointId);
    
    /**
     * 根据ID删除知识点记录
     * @param pointId 知识点ID
     * @return 影响的行数
     */
    int deleteById(@Param("pointId") Integer pointId);
    
    /**
     * 根据ID更新知识点记录
     * @param etKnowledgePoint 知识点实体对象
     * @return 影响的行数
     */
    int updateById(EtKnowledgePoint etKnowledgePoint);
    
    /**
     * 查询所有知识点记录
     * @return 知识点列表
     */
    List<EtKnowledgePoint> selectAll();
    
    /**
     * 根据知识点名称查询知识点记录
     * @param pointName 知识点名称
     * @return 知识点记录
     */
    EtKnowledgePoint selectByName(@Param("pointName") String pointName);
    
    /**
     * 根据领域ID查询知识点记录
     * @param fieldId 领域ID
     * @return 知识点列表
     */
    List<EtKnowledgePoint> selectByFieldId(@Param("fieldId") Integer fieldId);
    
    /**
     * 根据状态查询知识点记录
     * @param state 状态
     * @return 知识点列表
     */
    List<EtKnowledgePoint> selectByState(@Param("state") Integer state);
    
    /**
     * 根据名称模糊查询知识点记录
     * @param pointName 知识点名称
     * @return 知识点列表
     */
    List<EtKnowledgePoint> selectByNameLike(@Param("pointName") String pointName);
}