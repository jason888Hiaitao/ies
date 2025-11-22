package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "评论表实体类")
@Data
public class EtComment {
    @Schema(description = "评论ID", example = "1")
    private Integer commentId;
    @Schema(description = "题目ID", example = "1")
    private Integer questionId;
    @Schema(description = "索引ID", example = "1")
    private Integer indexId;
    @Schema(description = "用户ID", example = "1")
    private Integer userId;
    @Schema(description = "评论内容", example = "这是一条评论")
    private String contentMsg;
    @Schema(description = "引用ID", example = "1")
    private Integer quoteId;
    @Schema(description = "回复ID", example = "1")
    private Integer reId;
}
