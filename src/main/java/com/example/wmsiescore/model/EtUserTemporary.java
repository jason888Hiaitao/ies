package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;

@Schema(description = "临时用户表实体类")
@Data
public class EtUserTemporary {
    @Schema(description = "用户ID", example = "1")
    private Integer id;
    @Schema(description = "用户名", example = "temp_user")
    private String username;
    @Schema(description = "密码", example = "encrypted_password")
    private String password;
    @Schema(description = "邮箱", example = "temp@example.com")
    private String email;
    @Schema(description = "电话", example = "13800138000")
    private String phone;
    @Schema(description = "添加日期", example = "2023-01-01")
    private Date addDate;
    @Schema(description = "过期日期", example = "2024-01-01")
    private Date expireDate;
    @Schema(description = "添加人", example = "admin")
    private String addBy;
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
    @Schema(description = "是否外部用户", example = "false")
    private Boolean ifOutSource;
    @Schema(description = "真实姓名", example = "临时用户")
    private String truename;
    @Schema(description = "领域ID", example = "1")
    private String fieldId;
    @Schema(description = "省份", example = "北京")
    private String province;
    @Schema(description = "公司", example = "ABC公司")
    private String company;
    @Schema(description = "部门", example = "技术部")
    private String department;
    @Schema(description = "组名", example = "开发组")
    private String groupname;
    @Schema(description = "临时部门", example = "临时技术部")
    private String temporaryDepartment;
    @Schema(description = "临时组名", example = "临时开发组")
    private String temporaryGroupname;
}
