package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Schema(description = "用户分组成员表实体类")
@Data
public class EtUserGroupMember {
    @Schema(description = "成员ID", example = "1")
    private Long id;
    @Schema(description = "分组ID", example = "1")
    private Long groupId; // 分组ID
    @Schema(description = "用户ID", example = "1")
    private Long userId; // 用户ID
    @Schema(description = "用户姓名", example = "张三")
    private String userName; // 用户姓名
    @Schema(description = "用户账号", example = "zhangsan")
    private String userAccount; // 用户账号
    @Schema(description = "状态：active-激活，inactive-停用", example = "active")
    private String status; // 状态：active-激活，inactive-停用
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime; // 创建时间
    @Schema(description = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime; // 更新时间
}
