package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtQuestion2PointQuery;
import com.example.wmsiescore.model.EtQuestion2Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试题知识点关联表统一DAO接口
 * 提供试题知识点关联表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtQuestion2PointDao {
    
    /**
     * 插入试题知识点关联记录（只插入非空字段）
     * @param etQuestion2Point 试题知识点关联实体对象
     * @return 影响的行数
     */
    int insertSelective(EtQuestion2Point etQuestion2Point);
    
    /**
     * 插入或更新试题知识点关联记录（使用ON DUPLICATE KEY UPDATE）
     * @param etQuestion2Point 试题知识点关联实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtQuestion2Point etQuestion2Point);
    
    /**
     * 根据条件查询试题知识点关联列表
     * @param query 查询条件对象
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectByCondition(EtQuestion2PointQuery query);
    
    /**
     * 根据条件分页查询试题知识点关联列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectByConditionWithPage(EtQuestion2PointQuery query);
    
    /**
     * 根据条件统计试题知识点关联数量
     * @param query 查询条件对象
     * @return 试题知识点关联数量
     */
    int countByCondition(EtQuestion2PointQuery query);
    
    /**
     * 批量插入试题知识点关联记录
     * @param question2Points 试题知识点关联列表
     * @return 影响的行数
     */
    int batchInsert(@Param("question2Points") List<EtQuestion2Point> question2Points);
    
    /**
     * 批量删除试题知识点关联记录
     * @param question2PointIds 关联ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("question2PointIds") List<Integer> question2PointIds);
    
    /**
     * 根据条件更新试题知识点关联记录（只更新非空字段）
     * @param query 查询条件对象（必须包含question2PointId）
     * @return 影响的行数
     */
    int updateByCondition(EtQuestion2PointQuery query);
    
    /**
     * 根据ID查询试题知识点关联记录
     * @param question2PointId 关联ID
     * @return 试题知识点关联记录
     */
    EtQuestion2Point selectById(@Param("question2PointId") Integer question2PointId);
    
    /**
     * 根据ID删除试题知识点关联记录
     * @param question2PointId 关联ID
     * @return 影响的行数
     */
    int deleteById(@Param("question2PointId") Integer question2PointId);
    
    /**
     * 根据ID更新试题知识点关联记录
     * @param etQuestion2Point 试题知识点关联实体对象
     * @return 影响的行数
     */
    int updateById(EtQuestion2Point etQuestion2Point);
    
    /**
     * 查询所有试题知识点关联记录
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectAll();
    
    /**
     * 根据试题ID查询试题知识点关联记录
     * @param questionId 试题ID
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectByQuestionId(@Param("questionId") Integer questionId);
    
    /**
     * 根据知识点ID查询试题知识点关联记录
     * @param pointId 知识点ID
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectByPointId(@Param("pointId") Integer pointId);
    
    /**
     * 根据试题ID和知识点ID查询试题知识点关联记录
     * @param questionId 试题ID
     * @param pointId 知识点ID
     * @return 试题知识点关联记录
     */
    EtQuestion2Point selectByQuestionIdAndPointId(
        @Param("questionId") Integer questionId,
        @Param("pointId") Integer pointId
    );
    
    /**
     * 根据试题ID列表查询试题知识点关联记录
     * @param questionIds 试题ID列表
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectByQuestionIds(@Param("questionIds") List<Integer> questionIds);
    
    /**
     * 根据知识点ID列表查询试题知识点关联记录
     * @param pointIds 知识点ID列表
     * @return 试题知识点关联列表
     */
    List<EtQuestion2Point> selectByPointIds(@Param("pointIds") List<Integer> pointIds);
    
    /**
     * 根据试题ID删除试题知识点关联记录
     * @param questionId 试题ID
     * @return 影响的行数
     */
    int deleteByQuestionId(@Param("questionId") Integer questionId);
    
    /**
     * 根据知识点ID删除试题知识点关联记录
     * @param pointId 知识点ID
     * @return 影响的行数
     */
    int deleteByPointId(@Param("pointId") Integer pointId);
}