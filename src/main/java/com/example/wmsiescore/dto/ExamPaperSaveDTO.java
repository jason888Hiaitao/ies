package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 试卷保存DTO
 */
@Data
@ApiModel(value = "ExamPaperSaveDTO", description = "试卷保存请求对象")
public class ExamPaperSaveDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "操作类型", example = "create", allowableValues = "create,update,delete,batch-delete")
    private String operation;
    
    @ApiModelProperty(value = "试卷ID，更新时必填，创建时为空", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "试卷名称", example = "Java基础测试卷", required = true)
    private String name;
    
    @ApiModelProperty(value = "试卷内容", example = "试卷JSON内容")
    private String content;
    
    @ApiModelProperty(value = "考试时长（分钟）", example = "120")
    private Integer duration;
    
    @ApiModelProperty(value = "及格分数", example = "60.0")
    private BigDecimal passPoint;
    
    @ApiModelProperty(value = "总分数", example = "100.0")
    private BigDecimal totalPoint;
    
    @ApiModelProperty(value = "试卷状态", example = "active")
    private String status;
    
    @ApiModelProperty(value = "试卷摘要", example = "Java基础知识测试")
    private String summary;
    
    @ApiModelProperty(value = "是否可见", example = "true")
    private Boolean isVisible;
    
    @ApiModelProperty(value = "答题卡内容", example = "答题卡JSON内容")
    private String answerSheet;
    
    @ApiModelProperty(value = "所属分组ID", example = "1")
    private Long groupId;
    
    @ApiModelProperty(value = "是否主观题试卷", example = "false")
    private Boolean isSubjective;
    
    @ApiModelProperty(value = "创建人", example = "admin")
    private String creator;
    
    @ApiModelProperty(value = "试卷类型", example = "正式考试")
    private String paperType;
    
    @ApiModelProperty(value = "所属领域ID", example = "1")
    private Long fieldId;
    
    @ApiModelProperty(value = "有效来源", example = "内部题库")
    private String validsource;
    
    @ApiModelProperty(value = "有效部门", example = "技术部")
    private String validdpt;
    
    @ApiModelProperty(value = "考试次数", example = "3")
    private Integer examCount;
    
    @ApiModelProperty(value = "答案是否隐藏", example = "false")
    private Boolean answerHide;
    
    @ApiModelProperty(value = "批量删除的ID列表", example = "[1,2,3]")
    private List<Long> ids;
}