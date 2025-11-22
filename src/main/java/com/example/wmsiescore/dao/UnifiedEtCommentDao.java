package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtCommentQuery;
import com.example.wmsiescore.model.EtComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论表统一DAO接口
 * 提供评论表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtCommentDao {
    
    /**
     * 插入评论记录（只插入非空字段）
     * @param etComment 评论实体对象
     * @return 影响的行数
     */
    int insertSelective(EtComment etComment);
    
    /**
     * 插入或更新评论记录（使用ON DUPLICATE KEY UPDATE）
     * @param etComment 评论实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtComment etComment);
    
    /**
     * 根据条件查询评论列表
     * @param query 查询条件对象
     * @return 评论列表
     */
    List<EtComment> selectByCondition(EtCommentQuery query);
    
    /**
     * 根据条件分页查询评论列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 评论列表
     */
    List<EtComment> selectByConditionWithPage(EtCommentQuery query);
    
    /**
     * 根据条件统计评论数量
     * @param query 查询条件对象
     * @return 评论数量
     */
    int countByCondition(EtCommentQuery query);
    
    /**
     * 批量插入评论记录
     * @param comments 评论列表
     * @return 影响的行数
     */
    int batchInsert(@Param("comments") List<EtComment> comments);
    
    /**
     * 批量删除评论记录
     * @param commentIds 评论ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("commentIds") List<Long> commentIds);
    
    /**
     * 根据条件更新评论记录（只更新非空字段）
     * @param query 查询条件对象（必须包含commentId）
     * @return 影响的行数
     */
    int updateByCondition(EtCommentQuery query);
    
    /**
     * 根据ID查询评论记录
     * @param commentId 评论ID
     * @return 评论记录
     */
    EtComment selectById(@Param("commentId") Long commentId);
    
    /**
     * 根据ID删除评论记录
     * @param commentId 评论ID
     * @return 影响的行数
     */
    int deleteById(@Param("commentId") Long commentId);
    
    /**
     * 根据ID更新评论记录
     * @param etComment 评论实体对象
     * @return 影响的行数
     */
    int updateById(EtComment etComment);
    
    /**
     * 查询所有评论记录
     * @return 评论列表
     */
    List<EtComment> selectAll();
}