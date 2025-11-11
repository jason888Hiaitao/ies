package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtKnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识点Mapper接口
 */
@Mapper
public interface EtKnowledgePointMapper {
    
    /**
     * 根据条件查询列表
     */
    List<EtKnowledgePoint> selectList(@Param("knowledgePoint") EtKnowledgePoint knowledgePoint);
    
    /**
     * 根据ID查询
     */
    EtKnowledgePoint selectById(@Param("pointId") Integer pointId);
    
    /**
     * 插入记录
     */
    int insert(EtKnowledgePoint knowledgePoint);
    
    /**
     * 根据ID更新
     */
    int updateById(EtKnowledgePoint knowledgePoint);
    
    /**
     * 根据ID删除
     */
    int deleteById(@Param("pointId") Integer pointId);
    
    /**
     * 批量删除
     */
    int deleteBatchIds(@Param("ids") List<Integer> ids);
    
    /**
     * 查询所有记录
     */
    List<EtKnowledgePoint> selectAll();
}