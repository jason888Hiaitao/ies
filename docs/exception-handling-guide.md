# 全局异常处理指南

## 概述

本项目实现了细化的全局异常处理机制，包括异常分类、统一响应、日志记录、监控告警等功能。

## 异常体系结构

### 1. 异常分类

#### 业务异常 (BusinessException)
- **用途**: 处理业务逻辑中的异常情况
- **子类**:
  - `ResourceNotFoundException`: 资源未找到
  - `ParameterValidationException`: 参数验证失败
  - `UnauthorizedException`: 未授权访问
  - `ForbiddenException`: 权限不足
  - `DataAccessException`: 数据访问异常

#### 系统异常 (SystemException)
- **用途**: 处理系统级别的异常
- **包括**: 数据库异常、网络异常、文件操作异常等

### 2. 响应码体系

#### 通用响应码 (200-599)
- `200`: 操作成功
- `400`: 请求参数错误
- `401`: 未授权
- `403`: 禁止访问
- `404`: 资源未找到
- `500`: 服务器内部错误

#### 业务异常响应码 (1001-1999)
- `1001`: 用户不存在
- `1002`: 试卷不存在
- `1003`: 题目不存在
- `1004`: 考试已开始，无法修改
- `1005`: 考试已结束
- `1006`: 重复提交
- `1007`: 无效的考试状态
- `1008`: 权限不足

#### 参数验证异常响应码 (2001-2999)
- `2001`: 参数格式错误
- `2002`: 缺少必需参数
- `2003`: 参数超出范围

#### 数据操作异常响应码 (3001-3999)
- `3001`: 数据保存失败
- `3002`: 数据更新失败
- `3003`: 数据删除失败
- `3004`: 数据查询失败

#### 文件操作异常响应码 (4001-4999)
- `4001`: 文件上传失败
- `4002`: 文件下载失败
- `4003`: 不支持的文件格式
- `4004`: 文件大小超出限制

#### 系统异常响应码 (5001-5999)
- `5001`: 系统繁忙，请稍后重试
- `5002`: 服务不可用
- `5003`: 数据库错误
- `5004`: 网络错误

## 使用指南

### 1. 基本使用

#### 抛出业务异常
```java
// 资源未找到
if (examPaper == null) {
    throw new ResourceNotFoundException("试卷", examPaperId);
}

// 参数验证失败
if (name == null || name.trim().isEmpty()) {
    throw new ParameterValidationException("name", "不能为空");
}

// 权限不足
if (!hasPermission(userId, "DELETE")) {
    throw new ForbiddenException("用户无删除权限");
}

// 自定义业务异常
if (exam.getStatus() == "FINISHED") {
    throw new BusinessException(ResponseCode.EXAM_ALREADY_FINISHED, "考试已结束");
}
```

#### 在Service层使用
```java
@Service
public class ExamPaperServiceImpl implements ExamPaperService {
    
    @Autowired
    private ExceptionMonitorUtil exceptionMonitorUtil;
    
    public void deleteExamPaper(Long id, String userId) {
        try {
            // 参数验证
            if (id == null) {
                throw new ParameterValidationException("id", "不能为空");
            }
            
            // 权限检查
            if (!hasPermission(userId, "DELETE_EXAM_PAPER")) {
                throw new ForbiddenException("无删除权限");
            }
            
            // 业务逻辑
            examPaperMapper.deleteExamPaper(id);
            
        } catch (BusinessException e) {
            // 记录异常
            ExceptionLogUtil.logException("warn", e, "删除试卷");
            exceptionMonitorUtil.recordException(e.getClass().getSimpleName(), "删除试卷");
            throw e; // 重新抛出，让全局异常处理器处理
        }
    }
}
```

### 2. 异常监控

#### 系统健康检查
```java
@Autowired
private ExceptionMonitorUtil exceptionMonitorUtil;

public boolean isSystemHealthy() {
    return exceptionMonitorUtil.isSystemHealthy();
}
```

#### 异常统计
```java
public String getExceptionStats() {
    return exceptionMonitorUtil.getExceptionStatistics();
}
```

### 3. 日志记录

#### 自动日志记录
全局异常处理器会自动记录异常信息，包括：
- 请求URL和方法
- 客户端IP地址
- 请求参数（敏感参数已脱敏）
- 异常堆栈信息

#### 手动日志记录
```java
try {
    // 业务逻辑
} catch (BusinessException e) {
    ExceptionLogUtil.logException("warn", e, "操作描述");
    throw e;
}
```

## 配置说明

### 1. Redis配置（可选）
如果需要异常监控和告警功能，需要配置Redis：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
```

### 2. 异常监控配置
```java
// 在ExceptionMonitorUtil中可配置的参数
private static final int ALERT_THRESHOLD = 10; // 告警阈值
private static final int ALERT_WINDOW_MINUTES = 5; // 时间窗口
```

## 最佳实践

### 1. 异常处理原则
- **具体化**: 使用具体的异常类型，而不是通用的Exception
- **有意义**: 提供清晰的错误信息，便于问题定位
- **一致性**: 保持异常处理的一致性
- **安全性**: 避免在错误信息中泄露敏感信息

### 2. 异常分类建议
- **业务异常**: 可预期的业务规则违反
- **系统异常**: 不可预期的系统错误
- **参数异常**: 输入参数验证失败
- **权限异常**: 认证和授权失败

### 3. 日志记录建议
- **级别选择**: 
  - ERROR: 系统错误、数据库异常
  - WARN: 业务异常、参数验证失败
  - INFO: 重要的业务操作
- **信息完整**: 包含足够的上下文信息
- **敏感信息**: 避免记录密码、token等敏感信息

### 4. 监控告警建议
- **阈值设置**: 根据业务特点设置合理的告警阈值
- **告警渠道**: 支持邮件、短信、钉钉等多种告警方式
- **告警频率**: 避免告警风暴，设置告警间隔

## 扩展功能

### 1. 自定义异常类型
```java
public class CustomBusinessException extends BusinessException {
    public CustomBusinessException(String message) {
        super(ResponseCode.CUSTOM_ERROR, message);
    }
}
```

### 2. 自定义告警方式
```java
@Component
public class CustomAlertService {
    
    public void sendAlert(String message) {
        // 实现自定义告警逻辑
        // 如：发送到企业微信、Slack等
    }
}
```

### 3. 异常数据分析
```java
@Service
public class ExceptionAnalysisService {
    
    public Map<String, Long> analyzeExceptionTrends() {
        // 分析异常趋势
        return new HashMap<>();
    }
    
    public List<String> getTopExceptions() {
        // 获取最频繁的异常
        return new ArrayList<>();
    }
}
```

## 测试指南

### 1. 单元测试
```java
@Test
public void testResourceNotFoundException() {
    ResourceNotFoundException exception = new ResourceNotFoundException("试卷", 123L);
    assertEquals(ResponseCode.NOT_FOUND.getCode(), exception.getCode());
    assertEquals("试卷不存在: 123", exception.getMessage());
}
```

### 2. 集成测试
```java
@SpringBootTest
@AutoConfigureTestDatabase
public class ExceptionHandlingIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testGlobalExceptionHandling() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/api/exam-papers/999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
```

## 故障排查

### 1. 常见问题

#### 问题1: 异常没有被全局处理器捕获
**原因**: 异常被try-catch捕获但没有重新抛出
**解决**: 确保业务异常能够传播到全局异常处理器

#### 问题2: 异常信息不完整
**原因**: 日志级别设置不当或异常信息被过滤
**解决**: 检查日志配置，确保异常信息完整记录

#### 问题3: 告警不及时
**原因**: Redis连接问题或阈值设置过高
**解决**: 检查Redis连接状态，调整告警阈值

### 2. 调试技巧
- 使用日志级别DEBUG查看详细信息
- 检查异常处理器的执行顺序
- 验证异常监控的Redis数据

## 性能优化

### 1. 异常处理性能
- 避免在异常处理中进行耗时操作
- 使用异步方式记录日志和发送告警
- 合理设置异常信息的详细程度

### 2. 监控性能
- 使用Redis的过期机制清理历史数据
- 批量处理异常统计信息
- 定期清理过期的告警记录

## 总结

本项目的全局异常处理机制提供了完整的异常管理解决方案，包括异常分类、统一响应、日志记录、监控告警等功能。通过合理使用这些功能，可以提高系统的稳定性和可维护性。