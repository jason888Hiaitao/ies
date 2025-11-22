package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 临时用户查询条件对象
 * 用于封装临时用户表的查询条件
 */
@Data
public class EtUserTemporaryQuery {
    
    /**
     * 临时用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户邮箱
     */
    private String email;
    
    /**
     * 用户手机号
     */
    private String phone;
    
    /**
     * 添加日期
     */
    private String addDate;
    
    /**
     * 过期日期
     */
    private String expireDate;
    
    /**
     * 添加人
     */
    private String addBy;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 是否外包
     */
    private Boolean ifOutSource;
    
    /**
     * 真实姓名
     */
    private String truename;
    
    /**
     * 领域ID
     */
    private Long fieldId;
    
    /**
     * 省份
     */
    private String province;
    
    /**
     * 公司
     */
    private String company;
    
    /**
     * 部门
     */
    private String department;
    
    /**
     * 组名
     */
    private String groupname;
    
    /**
     * 临时部门
     */
    private String temporaryDepartment;
    
    /**
     * 临时组名
     */
    private String temporaryGroupname;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}