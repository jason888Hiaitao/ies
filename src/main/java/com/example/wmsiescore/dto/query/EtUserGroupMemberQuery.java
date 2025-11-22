package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 用户组成员查询条件对象
 * 用于封装用户组成员表的查询条件
 */
@Data
public class EtUserGroupMemberQuery {
    
    /**
     * 用户组成员ID
     */
    private Long id;
    
    /**
     * 用户组ID
     */
    private Long userGroupId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 角色
     */
    private String role;
    
    /**
     * 加入时间
     */
    private String joinTime;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}