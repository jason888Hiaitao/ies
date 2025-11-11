package com.example.wmsiescore.dao;

import com.example.wmsiescore.model.EtQuestion2Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试题与知识点关联表DAO接口
 */
@Mapper
public interface EtQuestion2PointDao {
    
    /**
     * 插入关联记录
     */
    int insert(EtQuestion2Point etQuestion2Point);
    
    /**
     * 根据ID删除关联记录
     */
    int deleteById(@Param("question2PointId") Integer question2PointId);
    
    /**
     * 根据试题ID删除关联记录
     */
    int deleteByQuestionId(@Param("questionId") Integer questionId);
    
    /**
     * 根据知识点ID删除关联记录
     */
    int deleteByPointId(@Param("pointId") Integer pointId);
    
    /**
     * 更新关联记录
     */
    int updateById(EtQuestion2Point etQuestion2Point);
    
    /**
     * 根据ID查询关联记录
     */
    EtQuestion2Point selectById(@Param("question2PointId") Integer question2PointId);
    
    /**
     * 查询所有关联记录
     */
    List<EtQuestion2Point> selectAll();
    
    /**
     * 根据试题ID查询关联记录
     */
    List<EtQuestion2Point> selectByQuestionId(@Param("questionId") Integer questionId);
    
    /**
     * 根据知识点ID查询关联记录
     */
    List<EtQuestion2Point> selectByPointId(@Param("pointId") Integer pointId);
    
    /**
     * 批量插入关联记录
     */
    int batchInsert(@Param("list") List<EtQuestion2Point> list);
    
    /**
     * 批量删除关联记录
     */
    int deleteByIds(@Param("question2PointIds") List<Integer> question2PointIds);
    
    /**
     * 根据试题ID批量删除关联记录
     */
    int deleteByQuestionIds(@Param("questionIds") List<Integer> questionIds);
    
    /**
     * 根据知识点ID批量删除关联记录
     */
    int deleteByPointIds(@Param("pointIds") List<Integer> pointIds);
    
    /**
     * 统计记录总数
     */
    int countAll();
    
    /**
     * 根据试题ID统计记录数
     */
    int countByQuestionId(@Param("questionId") Integer questionId);
    
    /**
     * 根据知识点ID统计记录数
     */
    int countByPointId(@Param("pointId") Integer pointId);
    
    /**
     * 根据试题ID列表查询关联记录
     */
    List<EtQuestion2Point> selectByQuestionIds(@Param("questionIds") List<Integer> questionIds);
}