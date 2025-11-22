package com.example.wmsiescore.migration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 数据迁移请求DTO
 */
@Data
@Schema(description = "数据迁移请求")
public class MigrationRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "操作类型", example = "MIGRATE, PAUSE, RESUME, CANCEL, VALIDATE, REPORT, PROGRESS", required = true)
    @NotBlank(message = "操作类型不能为空")
    private String operation;
    
    @Schema(description = "表名列表", required = true)
    @NotEmpty(message = "表名列表不能为空")
    private List<String> tableNames;
    
    @Schema(description = "批量处理大小", example = "1000")
    private Integer batchSize;
    
    @Schema(description = "是否启用断点续传", example = "true")
    private Boolean enableResume;
    
    @Schema(description = "是否启用数据校验", example = "true")
    private Boolean enableValidation;
    
    @Schema(description = "校验抽样比例(0-1)", example = "0.1")
    private Double validationSampleRatio;
}
