package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 考试历史查询DTO
 */
@Data
@Schema(description = "考试历史查询请求对象")
public class ExamHistoryQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "试卷名称", example = "Java基础测试卷")
    private String examPaperName;
    
    @Schema(description = "部门", example = "技术部")
    private String department;
    
    @Schema(description = "团队", example = "开发组")
    private String groupname;
    
    @Schema(description = "页码，从1开始", example = "1", required = true)
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小", example = "10", required = true)
    private Integer pageSize = 10;
}
