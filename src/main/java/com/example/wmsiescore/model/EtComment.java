package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "EtComment", description = "评论表实体类")
@Data
public class EtComment {
    @ApiModelProperty(value = "评论ID", example = "1")
    private Integer commentId;
    @ApiModelProperty(value = "题目ID", example = "1")
    private Integer questionId;
    @ApiModelProperty(value = "索引ID", example = "1")
    private Integer indexId;
    @ApiModelProperty(value = "用户ID", example = "1")
    private Integer userId;
    @ApiModelProperty(value = "评论内容", example = "这是一条评论")
    private String contentMsg;
    @ApiModelProperty(value = "引用ID", example = "1")
    private Integer quoteId;
    @ApiModelProperty(value = "回复ID", example = "1")
    private Integer reId;
}