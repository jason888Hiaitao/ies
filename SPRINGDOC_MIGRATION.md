# Swagger到SpringDoc OpenAPI迁移指南

本项目已从Swagger 2迁移到SpringDoc OpenAPI 3，以下是主要变更：

## 依赖变更

### 旧依赖 (Swagger 2)
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

### 新依赖 (SpringDoc OpenAPI 3)
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.9</version>
</dependency>
```

## 注解变更

### 控制器注解
| Swagger 2 | SpringDoc OpenAPI 3 |
|-----------|-------------------|
| `@Api(tags = "用户管理", description = "用户管理接口")` | `@Tag(name = "用户管理", description = "用户管理接口")` |
| `@ApiOperation(value = "获取用户", notes = "根据ID获取用户信息")` | `@Operation(summary = "获取用户", description = "根据ID获取用户信息")` |
| `@ApiParam(value = "用户ID", required = true)` | `@Parameter(description = "用户ID", required = true)` |

### 模型注解
| Swagger 2 | SpringDoc OpenAPI 3 |
|-----------|-------------------|
| `@ApiModel(value = "User", description = "用户实体类")` | `@Schema(description = "用户实体类")` |
| `@ApiModelProperty(value = "用户ID", example = "1")` | `@Schema(description = "用户ID", example = "1")` |

## 配置变更

### 旧配置 (Swagger 2)
```properties
# Swagger Configuration
springfox.documentation.swagger-ui.enabled=true
springfox.documentation.enabled=true
```

### 新配置 (SpringDoc OpenAPI 3)
```properties
# SpringDoc OpenAPI Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```

## 访问地址变更

### Swagger 2
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API文档JSON: `http://localhost:8080/v2/api-docs`

### SpringDoc OpenAPI 3
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API文档JSON: `http://localhost:8080/api-docs`

## 配置类变更

### 旧配置类 (Swagger 2)
```java
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.wmsiescore.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("WMS IES Core API")
                .description("API documentation for WMS IES Core Project")
                .version("1.0.0")
                .build();
    }
}
```

### 新配置类 (SpringDoc OpenAPI 3)
```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WMS IES Core API")
                        .description("WMS IES Core项目的API文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("WMS Team")
                                .email("wms@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
```

## 注意事项

1. SpringDoc OpenAPI 3与Spring Boot 2.7+兼容性更好
2. SpringDoc OpenAPI 3支持OpenAPI 3.0规范，功能更强大
3. 所有Swagger注解已自动转换为SpringDoc OpenAPI注解
4. 如果需要自定义配置，请参考新的配置类`OpenApiConfig`

## 测试

项目启动后，可以通过以下地址访问API文档：
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- 测试接口: `http://localhost:8080/api/test?param=Hello`