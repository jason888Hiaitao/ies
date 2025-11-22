package com.example.wmsiescore.dto;

import com.example.wmsiescore.model.EtQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 试题保存DTO
 * 包含试题基本信息和关联的知识点ID列表
 */
@Schema(description = "试题保存DTO，包含试题基本信息和关联的知识点ID列表")
@Data
public class QuestionSaveDTO extends EtQuestion implements Serializable {
    
    private static final long serialVersionUID = 1L;

    
    /**
     * 操作类型：create/update/delete
     */
    @Schema(description = "操作类型：create/update/delete", example = "create")
    private String operation;
    

    /**
     * 关联的知识点ID列表
     */
    @Schema(description = "关联的知识点ID列表", example = "[1, 2, 3]")
    private List<Integer> knowledgePointIds;

    @Schema(description = "题库ID", example = "1")
    private Integer fieldIds;
}
