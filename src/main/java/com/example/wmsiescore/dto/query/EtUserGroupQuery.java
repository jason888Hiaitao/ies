package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 用户组查询条件对象
 * 用于封装用户组表的查询条件
 */
@Data
public class EtUserGroupQuery {
    
    /**
     * 用户组ID
     */
    private Long id;
    
    /**
     * 用户组名称
     */
    private String groupName;
    
    /**
     * 用户组描述
     */
    private String description;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}