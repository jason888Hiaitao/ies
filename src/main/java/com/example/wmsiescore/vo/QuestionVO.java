package com.example.wmsiescore.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 考题视图对象
 * 不包含参考答案等敏感信息
 */
@Data
public class QuestionVO {
    
    /**
     * 题目ID
     */
    private Long id;
    
    /**
     * 题目标题
     */
    private String title;
    
    /**
     * 题目内容
     */
    private String content;
    
    /**
     * 题目类型ID
     */
    private Long questionTypeId;
    
    /**
     * 题目类型名称
     */
    private String questionTypeName;
    
    /**
     * 难度等级
     */
    private String difficulty;
    
    /**
     * 分值
     */
    private BigDecimal score;
    
    /**
     * 分组ID
     */
    private Long groupId;
    
    /**
     * 创建者
     */
    private String creator;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 是否可见
     */
    private Boolean isVisible;
    
    /**
     * 暴露次数
     */
    private Integer exposeTimes;
    
    /**
     * 正确次数
     */
    private Integer rightTimes;
    
    /**
     * 错误次数
     */
    private Integer wrongTimes;
}