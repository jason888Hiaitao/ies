package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 试卷查询DTO
 */
@Data
@Schema(description = "试卷查询请求对象")
public class ExamPaperQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "试卷名称", example = "Java基础测试卷")
    private String name;
    
    @Schema(description = "试卷状态", example = "active")
    private String status;
    
    @Schema(description = "有效部门", example = "技术部")
    private String validdpt;
    
    @Schema(description = "有效来源", example = "内部题库")
    private String validsource;
    
    @Schema(description = "试卷类型", example = "正式考试")
    private String paperType;
    
    @Schema(description = "页码，从1开始", example = "1", required = true)
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小", example = "10", required = true)
    private Integer pageSize = 10;
}
