package com.example.wmsiescore.common;

import lombok.Getter;

/**
 * 系统响应码枚举
 * 定义层次化的错误码体系，包含错误码、错误信息和解决方案建议
 */
@Getter
public enum ResponseCode {
    // ==================== 通用响应码 ====================
    SUCCESS(200, "操作成功", "操作已完成"),
    BAD_REQUEST(400, "请求参数错误", "请检查请求参数格式"),
    UNAUTHORIZED(401, "未授权", "请先登录或检查权限"),
    FORBIDDEN(403, "禁止访问", "您没有权限访问该资源"),
    NOT_FOUND(404, "资源未找到", "请检查资源ID或路径是否正确"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误", "请联系系统管理员"),
    
    // ==================== 业务错误 (1000-1999) ====================
    
    // 用户相关错误 (1001-1099)
    USER_NOT_FOUND(1001, "用户不存在", "请检查用户ID是否正确"),
    USER_ALREADY_EXISTS(1002, "用户已存在", "请使用其他用户名或邮箱"),
    USER_PASSWORD_ERROR(1003, "密码错误", "请检查密码是否正确"),
    USER_STATUS_DISABLED(1004, "用户已被禁用", "请联系管理员启用账户"),
    
    // 试卷相关错误 (1100-1199)
    EXAM_PAPER_NOT_FOUND(1101, "试卷不存在", "请检查试卷ID或联系管理员"),
    EXAM_PAPER_ALREADY_EXISTS(1102, "试卷已存在", "请使用其他试卷名称"),
    EXAM_PAPER_STATUS_INVALID(1103, "试卷状态无效", "请检查试卷状态"),
    EXAM_PAPER_EXPIRED(1104, "试卷已过期", "请选择其他有效试卷"),
    
    // 题目相关错误 (1200-1299)
    QUESTION_NOT_FOUND(1201, "题目不存在", "请检查题目ID或联系管理员"),
    QUESTION_TYPE_NOT_SUPPORTED(1202, "题目类型不支持", "请选择支持的题目类型"),
    QUESTION_CONTENT_INVALID(1203, "题目内容无效", "请检查题目内容格式"),
    
    // 考试相关错误 (1300-1399)
    EXAM_ALREADY_STARTED(1301, "考试已开始，无法修改", "考试开始后不允许修改"),
    EXAM_ALREADY_FINISHED(1302, "考试已结束", "考试已结束，无法进行操作"),
    DUPLICATE_SUBMISSION(1303, "重复提交", "请勿重复提交相同内容"),
    INVALID_EXAM_STATUS(1304, "无效的考试状态", "请检查考试状态"),
    PERMISSION_DENIED(1305, "权限不足", "请联系管理员获取相应权限"),
    EXAM_TIME_EXCEEDED(1306, "考试时间已到", "考试时间已结束，请及时提交"),
    
    // ==================== 参数验证错误 (2000-2999) ====================
    INVALID_PARAMETER(2001, "参数格式错误", "请检查参数格式是否符合要求"),
    MISSING_REQUIRED_PARAMETER(2002, "缺少必需参数", "请检查必填参数是否完整"),
    PARAMETER_OUT_OF_RANGE(2003, "参数超出范围", "请检查参数值是否在有效范围内"),
    PARAMETER_TYPE_MISMATCH(2004, "参数类型不匹配", "请检查参数类型是否正确"),
    
    // ==================== 数据操作错误 (3000-3999) ====================
    DATA_SAVE_FAILED(3001, "数据保存失败", "请检查数据完整性或联系管理员"),
    DATA_UPDATE_FAILED(3002, "数据更新失败", "请检查数据状态或联系管理员"),
    DATA_DELETE_FAILED(3003, "数据删除失败", "请检查数据依赖关系或联系管理员"),
    DATA_QUERY_FAILED(3004, "数据查询失败", "请检查查询条件或联系管理员"),
    DATA_CONSTRAINT_VIOLATION(3005, "数据约束冲突", "请检查数据唯一性约束"),
    
    // ==================== 文件操作错误 (4000-4999) ====================
    FILE_UPLOAD_FAILED(4001, "文件上传失败", "请检查文件格式和大小限制"),
    FILE_DOWNLOAD_FAILED(4002, "文件下载失败", "请检查文件是否存在或联系管理员"),
    FILE_FORMAT_NOT_SUPPORTED(4003, "不支持的文件格式", "请选择支持的文件格式"),
    FILE_SIZE_EXCEEDED(4004, "文件大小超出限制", "请压缩文件或选择较小文件"),
    FILE_NOT_FOUND(4005, "文件不存在", "请检查文件路径或联系管理员"),
    FILE_READ_PERMISSION_DENIED(4006, "文件读取权限不足", "请联系管理员获取文件权限"),
    
    // ==================== 系统错误 (5000-5999) ====================
    SYSTEM_BUSY(5001, "系统繁忙，请稍后重试", "系统当前负载较高，请稍后重试"),
    SERVICE_UNAVAILABLE(5002, "服务不可用", "服务暂时不可用，请稍后重试"),
    DATABASE_ERROR(5003, "数据库错误", "数据库操作异常，请联系管理员"),
    NETWORK_ERROR(5004, "网络错误", "网络连接异常，请检查网络设置"),
    CONFIGURATION_ERROR(5005, "配置错误", "系统配置异常，请联系管理员"),
    EXTERNAL_SERVICE_ERROR(5006, "外部服务错误", "依赖的外部服务异常，请稍后重试"),
    
    // ==================== 网络错误 (6000-6999) ====================
    NETWORK_TIMEOUT(6001, "网络超时", "网络请求超时，请检查网络连接"),
    NETWORK_CONNECTION_REFUSED(6002, "网络连接被拒绝", "目标服务拒绝连接，请检查服务状态"),
    NETWORK_DNS_ERROR(6003, "DNS解析错误", "域名解析失败，请检查网络配置");

    private final int code;
    private final String message;
    private final String suggestion;

    ResponseCode(int code, String message, String suggestion) {
        this.code = code;
        this.message = message;
        this.suggestion = suggestion;
    }
    
    /**
     * 根据错误码获取对应的枚举实例
     */
    public static ResponseCode fromCode(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.code == code) {
                return responseCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }
    
    /**
     * 验证错误码是否有效
     */
    public static boolean isValidCode(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.code == code) {
                return true;
            }
        }
        return false;
    }
}