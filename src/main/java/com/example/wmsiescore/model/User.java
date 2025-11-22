package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户表实体类")
@Data
public class User {
    @Schema(description = "用户ID", example = "1")
    private Long id;
    @Schema(description = "用户名", example = "admin")
    private String username;
    @Schema(description = "密码", example = "encrypted_password")
    private String password;
}