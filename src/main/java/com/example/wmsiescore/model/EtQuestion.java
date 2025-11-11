package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 试题表实体类
 */
@ApiModel(value = "EtQuestion", description = "试题表实体类")
@Data
public class EtQuestion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 题目ID，主键自增
     */
    @ApiModelProperty(value = "题目ID，主键自增", example = "1")
    private Long id;
    
    /**
     * 题目名称
     */
    @ApiModelProperty(value = "题目名称", example = "代数基础题")
    private String name;
    
    /**
     * 题目内容
     */
    @ApiModelProperty(value = "题目内容", example = "求解方程式：2x + 3 = 7")
    private String content;
    
    /**
     * 题目类型ID
     */
    @ApiModelProperty(value = "题目类型ID", example = "1")
    private Long questionTypeId;
    
    /**
     * 答题时长
     */
    @ApiModelProperty(value = "答题时长（分钟）", example = "30")
    private Integer duration;
    
    /**
     * 分值
     */
    @ApiModelProperty(value = "分值", example = "10.5")
    private BigDecimal points;
    
    /**
     * 所属分组ID
     */
    @ApiModelProperty(value = "所属分组ID", example = "1")
    private Long groupId;
    
    /**
     * 是否可见
     */
    @ApiModelProperty(value = "是否可见", example = "true")
    private Boolean isVisible;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "admin")
    private String creator;
    
    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间", example = "2023-01-01 10:00:00")
    private Timestamp lastModify;
    
    /**
     * 曝光次数
     */
    @ApiModelProperty(value = "曝光次数", example = "100")
    private Integer exposeTimes;
    
    /**
     * 答对次数
     */
    @ApiModelProperty(value = "答对次数", example = "80")
    private Integer rightTimes;
    
    /**
     * 答错次数
     */
    @ApiModelProperty(value = "答错次数", example = "20")
    private Integer wrongTimes;
    
    /**
     * 难度
     */
    @ApiModelProperty(value = "难度", example = "中等")
    private String difficulty;
    
    /**
     * 解析
     */
    @ApiModelProperty(value = "答案解析", example = "详细解析内容")
    private String analysis;
    
    /**
     * 参考答案
     */
    @ApiModelProperty(value = "参考答案", example = "A")
    private String reference;
    
    /**
     * 考查点
     */
    @ApiModelProperty(value = "考查点", example = "代数方程求解")
    private String examiningPoint;
    
    /**
     * 关键词
     */
    @ApiModelProperty(value = "关键词", example = "代数,方程,求解")
    private String keyword;
}