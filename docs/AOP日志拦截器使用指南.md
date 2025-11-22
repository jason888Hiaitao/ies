# AOP日志拦截器使用指南

## 概述

AOP日志拦截器是一个基于Spring AOP的日志记录组件，用于监控所有Controller层的接口请求。它可以记录完整的请求入参信息、方法返回的出参信息，并输出格式清晰的日志，包含调用时间、方法名、参数详情和返回结果。

## 功能特性

1. **自动拦截所有Controller方法**：通过AOP切面自动拦截所有Controller层的公共方法
2. **完整记录请求参数**：记录方法调用的所有参数，并过滤敏感信息
3. **记录返回结果**：记录方法返回的结果，并避免记录过大的响应体
4. **清晰的日志格式**：输出格式清晰的日志，包含请求ID、方法名、执行时间等
5. **异常处理**：妥善处理异常情况下的日志记录
6. **性能优化**：确保日志输出不影响原有业务逻辑的执行效率
7. **可配置性**：支持通过配置文件控制日志记录的详细程度
8. **选择性记录**：支持通过注解标记不需要记录日志的方法

## 配置说明

在`application.properties`文件中添加以下配置：

```properties
# AOP Log Aspect Configuration
log.aspect.enabled=true                    # 是否启用Controller层日志拦截
log.aspect.log-request-args=true           # 是否记录请求参数
log.aspect.log-response-result=true         # 是否记录返回结果
log.aspect.log-execution-time=true          # 是否记录方法执行时间
log.aspect.max-arg-length=200              # 请求参数最大记录长度
log.aspect.max-result-length=1000           # 返回结果最大记录长度
log.aspect.log-exception-stack-trace=true   # 是否记录异常堆栈信息
log.aspect.log-request-id=true              # 是否记录请求ID
```

## 使用方法

### 1. 自动记录日志

对于所有Controller层的公共方法，AOP日志拦截器会自动记录日志，无需额外配置：

```java
@RestController
@RequestMapping("/api/example")
public class ExampleController {
    
    @GetMapping("/hello")
    public ResponseResult<String> hello(@RequestParam String name) {
        return ResponseResult.success("Hello, " + name);
    }
}
```

上述方法会被自动记录日志，输出类似以下内容：

```
请求开始 [a1b2c3d4e5f6] - URI: /api/example/hello - Method: ExampleController.hello - Args: ["张三"]
请求结束 [a1b2c3d4e5f6] - Method: ExampleController.hello - ExecutionTime: 15ms - Result: {"code":200,"message":"操作成功","data":"Hello, 张三","timestamp":1625097600000}
```

### 2. 排除特定方法

如果某些方法不需要记录日志（如敏感接口或性能敏感接口），可以使用`@NoLog`注解：

```java
@RestController
@RequestMapping("/api/sensitive")
public class SensitiveController {
    
    @GetMapping("/data")
    @NoLog("这是一个敏感接口，不记录日志")
    public ResponseResult<String> getSensitiveData() {
        // 敏感数据处理
        return ResponseResult.success("敏感数据");
    }
}
```

使用`@NoLog`注解的方法不会被AOP日志拦截器记录日志。

### 3. 日志级别

AOP日志拦截器使用INFO级别记录正常请求日志，使用ERROR级别记录异常日志。确保日志框架的配置允许这些级别的日志输出。

## 日志格式说明

### 请求日志格式

```
请求开始 [请求ID] - URI: 请求URI - Method: 类名.方法名 - Args: [参数列表]
```

### 响应日志格式

```
请求结束 [请求ID] - Method: 类名.方法名 - ExecutionTime: 执行时间ms - Result: 返回结果
```

### 异常日志格式

```
请求异常 [请求ID] - Method: 类名.方法名 - ExecutionTime: 执行时间ms - Exception: 异常信息
```

## 参数和结果过滤

为了保护敏感信息和避免日志过大，AOP日志拦截器会对参数和结果进行过滤：

1. **敏感类型过滤**：自动过滤`HttpServletRequest`、`HttpServletResponse`和`MultipartFile`类型
2. **长度限制**：字符串类型超过配置的最大长度会被截断
3. **大数据过滤**：序列化后超过配置的最大长度的对象会被替换为类型信息
4. **ResponseResult特殊处理**：对于`ResponseResult`类型，只记录code和message，data部分根据大小决定是否记录

## 性能考虑

1. **异步处理**：日志记录使用同步方式，但尽量减少序列化和字符串操作
2. **条件判断**：在执行任何日志记录前，先检查是否启用日志记录
3. **延迟序列化**：只在需要记录日志时才进行JSON序列化
4. **长度限制**：通过限制参数和结果的长度，减少序列化和I/O开销

## 示例

参考`LogExampleController`类，其中包含了各种使用场景的示例：

1. 普通方法：会被AOP日志拦截器记录
2. 使用@NoLog注解的方法：不会被记录
3. 异常方法：展示异常日志记录
4. 长参数方法：展示参数截断
5. 大结果方法：展示结果截断

## 注意事项

1. 确保项目中已添加Spring AOP依赖
2. 确保日志框架配置正确，允许INFO和ERROR级别的日志输出
3. 对于性能敏感的接口，考虑使用@NoLog注解排除
4. 定期检查日志文件大小，避免日志文件过大
5. 在生产环境中，适当调整日志级别和记录详细程度

## 故障排除

### 1. 日志未输出

- 检查`log.aspect.enabled`配置是否为true
- 检查日志级别配置是否允许INFO级别输出
- 检查Controller类是否在`com.example.wmsiescore.controller`包下

### 2. 日志格式异常

- 检查FastJSON依赖是否正确引入
- 检查对象是否正确实现序列化接口

### 3. 性能问题

- 调整`log.aspect.max-arg-length`和`log.aspect.max-result-length`配置
- 对于性能敏感接口，使用@NoLog注解排除
- 考虑使用异步日志框架