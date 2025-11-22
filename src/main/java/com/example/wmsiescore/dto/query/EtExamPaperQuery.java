package com.example.wmsiescore.dto.query;

import lombok.Data;

/**
 * 试卷查询条件对象
 * 用于封装试卷表的查询条件
 */
@Data
public class EtExamPaperQuery {
    
    /**
     * 试卷ID
     */
    private Long id;
    
    /**
     * 试卷名称
     */
    private String name;
    
    /**
     * 考试时长
     */
    private Integer duration;
    
    /**
     * 及格分数
     */
    private Double passPoint;
    
    /**
     * 总分数
     */
    private Double totalPoint;
    
    /**
     * 试卷状态
     */
    private String status;
    
    /**
     * 是否可见
     */
    private Boolean isVisible;
    
    /**
     * 所属分组ID
     */
    private Long groupId;
    
    /**
     * 是否主观题试卷
     */
    private Boolean isSubjective;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 试卷类型
     */
    private String paperType;
    
    /**
     * 所属领域ID
     */
    private Long fieldId;
    
    /**
     * 有效来源
     */
    private String validsource;
    
    /**
     * 有效部门
     */
    private String validdpt;
    
    /**
     * 考试次数
     */
    private Integer examCount;
    
    /**
     * 答案是否隐藏
     */
    private Boolean answerHide;
    
    /**
     * 偏移量（用于分页）
     */
    private Integer offset;
    
    /**
     * 每页大小（用于分页）
     */
    private Integer pageSize;
}