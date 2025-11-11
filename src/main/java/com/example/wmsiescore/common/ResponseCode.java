package com.example.wmsiescore.common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    // 通用响应码
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源未找到"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    
    // 业务异常响应码
    USER_NOT_FOUND(1001, "用户不存在"),
    EXAM_PAPER_NOT_FOUND(1002, "试卷不存在"),
    QUESTION_NOT_FOUND(1003, "题目不存在"),
    EXAM_ALREADY_STARTED(1004, "考试已开始，无法修改"),
    EXAM_ALREADY_FINISHED(1005, "考试已结束"),
    DUPLICATE_SUBMISSION(1006, "重复提交"),
    INVALID_EXAM_STATUS(1007, "无效的考试状态"),
    PERMISSION_DENIED(1008, "权限不足"),
    
    // 参数验证异常
    INVALID_PARAMETER(2001, "参数格式错误"),
    MISSING_REQUIRED_PARAMETER(2002, "缺少必需参数"),
    PARAMETER_OUT_OF_RANGE(2003, "参数超出范围"),
    
    // 数据操作异常
    DATA_SAVE_FAILED(3001, "数据保存失败"),
    DATA_UPDATE_FAILED(3002, "数据更新失败"),
    DATA_DELETE_FAILED(3003, "数据删除失败"),
    DATA_QUERY_FAILED(3004, "数据查询失败"),
    
    // 文件操作异常
    FILE_UPLOAD_FAILED(4001, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(4002, "文件下载失败"),
    FILE_FORMAT_NOT_SUPPORTED(4003, "不支持的文件格式"),
    FILE_SIZE_EXCEEDED(4004, "文件大小超出限制"),
    
    // 系统异常
    SYSTEM_BUSY(5001, "系统繁忙，请稍后重试"),
    SERVICE_UNAVAILABLE(5002, "服务不可用"),
    DATABASE_ERROR(5003, "数据库错误"),
    NETWORK_ERROR(5004, "网络错误");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}