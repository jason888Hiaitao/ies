package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 试卷表实体类
 */
@Schema(description = "试卷表实体类")
@Data
public class EtExamPaper implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 试卷ID
     */
    @Schema(description = "试卷ID", example = "1")
    private Long id;
    
    /**
     * 试卷名称
     */
    @Schema(description = "试卷名称", example = "数学期末考试")
    private String name;
    
    /**
     * 试卷内容
     */
    @Schema(description = "试卷内容", example = "试卷详细内容")
    private String content;
    
    /**
     * 考试时长（分钟）
     */
    @Schema(description = "考试时长（分钟）", example = "120")
    private Integer duration;
    
    /**
     * 及格分数
     */
    @Schema(description = "及格分数", example = "60.0")
    private BigDecimal passPoint;
    
    /**
     * 总分数
     */
    @Schema(description = "总分数", example = "100.0")
    private BigDecimal totalPoint;
    
    /**
     * 试卷状态
     */
    @Schema(description = "试卷状态", example = "active")
    private String status;
    
    /**
     * 试卷摘要
     */
    @Schema(description = "试卷摘要", example = "试卷简要说明")
    private String summary;
    
    /**
     * 是否可见
     */
    @Schema(description = "是否可见", example = "true")
    private Boolean isVisible;
    
    /**
     * 答题卡内容
     */
    @Schema(description = "答题卡内容", example = "答题卡配置")
    private String answerSheet;
    
    /**
     * 所属分组ID
     */
    @Schema(description = "所属分组ID", example = "1")
    private Long groupId;
    
    /**
     * 是否主观题试卷
     */
    @Schema(description = "是否主观题试卷", example = "false")
    private Boolean isSubjective;
    
    /**
     * 创建人
     */
    @Schema(description = "创建人", example = "admin")
    private String creator;
    
    /**
     * 试卷类型
     */
    @Schema(description = "试卷类型", example = "期末考试")
    private String paperType;
    
    /**
     * 所属领域ID
     */
    @Schema(description = "所属领域ID", example = "1")
    private Long fieldId;
    
    /**
     * 有效来源
     */
    @Schema(description = "有效来源", example = "教务处")
    private String validsource;
    
    /**
     * 有效部门
     */
    @Schema(description = "有效部门", example = "数学系")
    private String validdpt;
    
    /**
     * 考试次数
     */
    @Schema(description = "考试次数", example = "10")
    private Integer examCount;
    
    /**
     * 答案是否隐藏
     */
    @Schema(description = "答案是否隐藏", example = "false")
    private Boolean answerHide;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
}
