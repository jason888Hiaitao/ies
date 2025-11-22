package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 考试历史详情DTO
 */
@Data
@Schema(description = "考试历史详情对象")
public class ExamHistoryDetailDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "历史记录ID", example = "1")
    private Long histId;
    
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    
    @Schema(description = "用户名", example = "admin")
    private String username;
    
    @Schema(description = "真实姓名", example = "张三")
    private String truename;
    
    @Schema(description = "部门", example = "技术部")
    private String department;
    
    @Schema(description = "团队", example = "开发组")
    private String groupname;
    
    @Schema(description = "试卷ID", example = "1")
    private Long examPaperId;
    
    @Schema(description = "试卷名称", example = "Java基础测试卷")
    private String examPaperName;
    
    @Schema(description = "考试内容", example = "考试详细内容")
    private String content;
    
    @Schema(description = "答题卡", example = "答题卡配置")
    private String answerSheet;
    
    @Schema(description = "考试时长（分钟）", example = "120")
    private Integer duration;
    
    @Schema(description = "提交时间", example = "2023-01-01 12:00:00")
    private Timestamp submitTime;
    
    @Schema(description = "获得分数", example = "85.5")
    private BigDecimal pointGet;
    
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
}
