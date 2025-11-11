package com.example.wmsiescore.enums;

/**
 * 题目类型枚举
 * 定义考试中不同类型的题目
 */
public enum QuestionTypeEnum {
    
    /**
     * 单选题
     */
    SINGLE_CHOICE(1, "单选题"),
    
    /**
     * 多选题
     */
    MULTIPLE_CHOICE(2, "多选题"),
    
    /**
     * 判断题
     */
    TRUE_FALSE(3, "判断题"),
    
    /**
     * 填空题
     */
    FILL_BLANK(4, "填空题"),
    
    /**
     * 问答题
     */
    ESSAY(5, "问答题"),
    
    /**
     * 编程题
     */
    PROGRAMMING(6, "编程题");
    
    private final Integer code;
    private final String description;
    
    QuestionTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 获取题目类型代码
     * @return 类型代码
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 获取题目类型描述
     * @return 类型描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据代码获取题目类型枚举
     * @param code 类型代码
     * @return 题目类型枚举
     */
    public static QuestionTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        
        for (QuestionTypeEnum type : QuestionTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 根据代码获取题目类型描述
     * @param code 类型代码
     * @return 类型描述
     */
    public static String getDescriptionByCode(Integer code) {
        QuestionTypeEnum type = getByCode(code);
        return type != null ? type.getDescription() : null;
    }
    
    /**
     * 判断是否为选择题类型
     * @return true如果是选择题（单选或多选）
     */
    public boolean isChoiceType() {
        return this == SINGLE_CHOICE || this == MULTIPLE_CHOICE;
    }
    
    /**
     * 判断是否为客观题
     * @return true如果是客观题（单选、多选、判断）
     */
    public boolean isObjectiveType() {
        return this == SINGLE_CHOICE || this == MULTIPLE_CHOICE || this == TRUE_FALSE;
    }
    
    /**
     * 判断是否为主观题
     * @return true如果是主观题（填空、问答、编程）
     */
    public boolean isSubjectiveType() {
        return this == FILL_BLANK || this == ESSAY || this == PROGRAMMING;
    }
}