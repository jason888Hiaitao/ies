package com.example.wmsiescore.enums;

import lombok.Getter;

/**
 * 试卷状态枚举
 */
@Getter
public enum ExamPaperStatusEnum {
    
    UNPUBLISH("unpublish", "未发布"),
    PUBLISHED("published", "已发布"),
    DRAFT("draft", "草稿"),
    ARCHIVED("archived", "已归档");
    
    private final String code;
    private final String description;
    
    ExamPaperStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据code获取枚举
     * @param code 状态码
     * @return 试卷状态枚举
     */
    public static ExamPaperStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ExamPaperStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}