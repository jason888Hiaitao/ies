# 问题答案DTO使用说明

## 概述
本项目新增了问题答案相关的DTO类，用于标准化考试提交数据的格式。

## 新增文件

### 1. QuestionAnswerDTO.java
**位置**: `src/main/java/com/example/wmsiescore/dto/QuestionAnswerDTO.java`

**功能**: 单个问题答案的数据传输对象

**字段**:
- `questionId`: 问题ID (Long)
- `answer`: 用户答案 (String)

**使用示例**:
```java
QuestionAnswerDTO answer = new QuestionAnswerDTO(1L, "A");
// 或者
QuestionAnswerDTO answer = new QuestionAnswerDTO();
answer.setQuestionId(1L);
answer.setAnswer("A");
```

### 2. ExamSubmissionDTO.java
**位置**: `src/main/java/com/example/wmsiescore/dto/ExamSubmissionDTO.java`

**功能**: 完整考试提交的数据传输对象

**字段**:
- `examHistoryId`: 考试记录ID (Long)
- `userId`: 用户ID (Long)
- `examPaperId`: 试卷ID (Long)
- `answers`: 答案列表 (List<QuestionAnswerDTO>)

**使用示例**:
```java
List<QuestionAnswerDTO> answers = Arrays.asList(
    new QuestionAnswerDTO(1L, "A"),
    new QuestionAnswerDTO(2L, "B,C")
);

ExamSubmissionDTO submission = new ExamSubmissionDTO(123L, 456L, 789L, answers);
```

## API接口

### 1. 提交考试
**URL**: `POST /api/exam/submit`

**请求体**:
```json
{
  "examHistoryId": 123,
  "userId": 456,
  "examPaperId": 789,
  "answers": [
    {
      "questionId": 1,
      "answer": "A"
    },
    {
      "questionId": 2,
      "answer": "B,C"
    }
  ]
}
```

**参数说明**:
- `examHistoryId`: 考试记录ID（必填）
- `userId`: 用户ID（必填）
- `examPaperId`: 试卷ID（必填）
- `answers`: 答案列表（可选）

### 2. 提交考试 (Map格式)
**URL**: `POST /api/exam/submit/map?recordId=123`

**请求体**:
```json
{
  "1": "A",
  "2": "B,C",
  "3": "Java"
}
```

## 服务层方法

### ExamService接口新增方法
```java
// 使用DTO提交考试
boolean submitExam(ExamSubmissionDTO examSubmission);

// 使用Map提交考试
boolean submitExam(Long recordId, Map<Long, String> userAnswers);
```

### ExamServiceImpl实现
- 支持三种不同的提交格式
- 自动转换DTO为Map格式进行统一处理
- 保持与原有JSON字符串格式的兼容性

## 使用建议

1. **统一接口**: 使用 `ExamSubmitRequestDTO` 对象作为入参，结构化数据，类型安全
2. **Map格式**: 适合简单的键值对场景，使用独立接口 `/api/exam/submit/map`
3. **参数简化**: 移除了 `answers` 字符串参数，统一使用对象传参

## 接口优化说明

`submitExam` 方法已优化：
- 移除了 `answers` 字符串入参
- 将所有入参合并为 `ExamSubmitRequestDTO` 对象
- 简化了接口调用，提高了类型安全性
- 推荐使用新的统一接口 `/api/exam/submit`

## 测试示例

参考 `ApiExample.java` 文件中的完整示例代码。

## 注意事项

1. 所有字段都应进行非空验证
2. 多选题答案使用逗号分隔的选项标识
3. 问答题答案长度建议大于10个字符
4. 考试提交后会自动计算得分和考试时长