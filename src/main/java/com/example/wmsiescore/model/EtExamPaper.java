package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 试卷表实体类
 */
@ApiModel(value = "EtExamPaper", description = "试卷表实体类")
@Data
public class EtExamPaper implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 试卷ID
     */
    @ApiModelProperty(value = "试卷ID", example = "1")
    private Long id;
    
    /**
     * 试卷名称
     */
    @ApiModelProperty(value = "试卷名称", example = "数学期末考试")
    private String name;
    
    /**
     * 试卷内容
     */
    @ApiModelProperty(value = "试卷内容", example = "试卷详细内容")
    private String content;
    
    /**
     * 考试时长（分钟）
     */
    @ApiModelProperty(value = "考试时长（分钟）", example = "120")
    private Integer duration;
    
    /**
     * 及格分数
     */
    @ApiModelProperty(value = "及格分数", example = "60.0")
    private BigDecimal passPoint;
    
    /**
     * 总分数
     */
    @ApiModelProperty(value = "总分数", example = "100.0")
    private BigDecimal totalPoint;
    
    /**
     * 试卷状态
     */
    @ApiModelProperty(value = "试卷状态", example = "active")
    private String status;
    
    /**
     * 试卷摘要
     */
    @ApiModelProperty(value = "试卷摘要", example = "试卷简要说明")
    private String summary;
    
    /**
     * 是否可见
     */
    @ApiModelProperty(value = "是否可见", example = "true")
    private Boolean isVisible;
    
    /**
     * 答题卡内容
     */
    @ApiModelProperty(value = "答题卡内容", example = "答题卡配置")
    private String answerSheet;
    
    /**
     * 所属分组ID
     */
    @ApiModelProperty(value = "所属分组ID", example = "1")
    private Long groupId;
    
    /**
     * 是否主观题试卷
     */
    @ApiModelProperty(value = "是否主观题试卷", example = "false")
    private Boolean isSubjective;
    
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "admin")
    private String creator;
    
    /**
     * 试卷类型
     */
    @ApiModelProperty(value = "试卷类型", example = "期末考试")
    private String paperType;
    
    /**
     * 所属领域ID
     */
    @ApiModelProperty(value = "所属领域ID", example = "1")
    private Long fieldId;
    
    /**
     * 有效来源
     */
    @ApiModelProperty(value = "有效来源", example = "教务处")
    private String validsource;
    
    /**
     * 有效部门
     */
    @ApiModelProperty(value = "有效部门", example = "数学系")
    private String validdpt;
    
    /**
     * 考试次数
     */
    @ApiModelProperty(value = "考试次数", example = "10")
    private Integer examCount;
    
    /**
     * 答案是否隐藏
     */
    @ApiModelProperty(value = "答案是否隐藏", example = "false")
    private Boolean answerHide;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
}