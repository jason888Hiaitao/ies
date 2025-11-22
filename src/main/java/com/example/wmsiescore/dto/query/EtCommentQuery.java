package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 评论查询条件对象
 * 用于封装评论表的查询条件
 */
@Data
public class EtCommentQuery {
    
    /**
     * 评论ID
     */
    private Long commentId;
    
    /**
     * 题目ID
     */
    private Long questionId;
    
    /**
     * 评论索引
     */
    private Integer commentIndex;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 评论内容
     */
    private String contentMsg;
    
    /**
     * 引用的评论ID
     */
    private Long quoteId;
    
    /**
     * 回复的评论ID
     */
    private Long reId;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}