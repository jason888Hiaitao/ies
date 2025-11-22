package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Schema(description = "用户表实体类")
@Data
public class EtUser {
    @Schema(description = "用户ID", example = "1")
    private Long id;
    @Schema(description = "用户名", example = "admin")
    private String username;
    @Schema(description = "密码", example = "encrypted_password")
    private String password;
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;
    @Schema(description = "电话", example = "13800138000")
    private String phone;
    @Schema(description = "添加日期", example = "2023-01-01 10:00:00")
    private Timestamp addDate;
    @Schema(description = "过期日期", example = "2024-01-01 10:00:00")
    private Timestamp expireDate;
    @Schema(description = "添加人", example = "system")
    private String addBy;
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
    @Schema(description = "是否外部用户", example = "false")
    private Boolean ifOutSource;
    @Schema(description = "真实姓名", example = "张三")
    private String truename;
    @Schema(description = "领域ID", example = "1")
    private Long fieldId;
    @Schema(description = "省份", example = "北京")
    private String province;
    @Schema(description = "公司", example = "ABC公司")
    private String company;
    @Schema(description = "部门", example = "技术部")
    private String department;
    @Schema(description = "组名", example = "开发组")
    private String groupname;
}
