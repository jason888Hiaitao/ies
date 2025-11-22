# 待考试列表功能重构文档

## 重构概述

本次重构重新设计了待考试列表的查询逻辑，按照以下业务规则实现：

1. **先查询用户下的所有考试记录**（提交时间不为空的）
2. **然后查询用户可见试卷**（is_visible=1，试卷状态=1，且试卷与用户是同一个部门validdpt或者该试卷与用户是同一个群组validsource）
3. **用户即可见，又没考过的试卷就是待考试列表**

## 涉及的数据表

- `et_user` - 用户表
- `et_exam_paper` - 试卷表
- `et_user_exam_history` - 考试历史记录表
- `et_user_temporary` - 用户群组表

## 核心业务逻辑

### 1. 已完成试卷查询
```sql
SELECT DISTINCT exam_paper_id 
FROM et_user_exam_history 
WHERE user_id = #{userId}
AND submit_time IS NOT NULL
```

### 2. 可见试卷查询
```sql
SELECT * FROM et_exam_paper ep
WHERE ep.is_visible = 1
AND ep.paper_status = 1
AND (
    -- 部门匹配
    (ep.valid_dpt IS NOT NULL AND ep.valid_dpt != '' 
     AND ep.valid_dpt LIKE CONCAT('%', (SELECT department FROM et_user WHERE id = #{userId}), '%'))
    OR 
    -- 群组匹配
    (ep.valid_source IS NOT NULL AND ep.valid_source != '' 
     AND (
         ep.valid_source LIKE CONCAT('%', (SELECT groupname FROM et_user WHERE id = #{userId}), '%')
         OR
         ep.valid_source LIKE CONCAT('%', (SELECT temporary_groupname FROM et_user_temporary WHERE username = (SELECT username FROM et_user WHERE id = #{userId})), '%')
     ))
)
```

### 3. 待考试列表查询
```sql
-- 结合可见试卷和已完成试卷，获取待考试列表
SELECT * FROM et_exam_paper ep
WHERE ep.id IN (可见试卷ID列表)
AND ep.id NOT IN (已完成试卷ID列表)
```

## 新增字段说明

### et_exam_paper 表新增字段
- `is_visible` - 是否可见：0-不可见，1-可见
- `paper_status` - 试卷状态：0-禁用，1-启用
- `valid_dpt` - 有效部门（多个部门用逗号分隔）
- `valid_source` - 有效群组（多个群组用逗号分隔）

### et_user_exam_history 表新增字段
- `submit_time` - 提交时间

## API接口

### 1. 获取用户已完成的试卷ID列表
```
GET /api/pendingExam/getCompletedExamPaperIds/{userId}
```

### 2. 获取用户可见的试卷列表
```
GET /api/pendingExam/getVisibleExamPapers/{userId}
```

### 3. 获取用户待参加的考试列表
```
GET /api/pendingExam/getPendingExams/{userId}
```

## 代码结构

### Service层
- `PendingExamService` - 待考试列表服务接口
- `PendingExamServiceImpl` - 待考试列表服务实现

### Controller层
- `PendingExamController` - 待考试列表控制器

### Mapper层
- `EtExamPaperMapper` - 新增三个查询方法：
  - `getCompletedExamPaperIds()`
  - `getVisibleExamPapersForUser()`
  - `getPendingExamsForUser()`

## 测试用例

提供了完整的单元测试 `PendingExamServiceTest`，包含：
- 已完成试卷查询测试
- 可见试卷查询测试
- 待考试列表查询测试
- 完整业务逻辑验证测试

## 性能优化

### 数据库索引
```sql
CREATE INDEX idx_exam_paper_visible ON et_exam_paper(is_visible, paper_status);
CREATE INDEX idx_exam_paper_valid_dpt ON et_exam_paper(valid_dpt);
CREATE INDEX idx_exam_paper_valid_source ON et_exam_paper(valid_source);
CREATE INDEX idx_user_exam_history_submit ON et_user_exam_history(user_id, submit_time);
CREATE INDEX idx_user_department ON et_user(department);
CREATE INDEX idx_user_groupname ON et_user(groupname);
CREATE INDEX idx_user_temporary_group ON et_user_temporary(temporary_groupname);
```

## 部署说明

1. 执行数据库脚本 `src/main/resources/sql/pending_exam_schema.sql`
2. 重新编译部署应用
3. 验证API接口功能

## 注意事项

1. **数据一致性**：确保 `et_user` 和 `et_user_temporary` 表中的用户信息保持一致
2. **性能考虑**：对于大量数据，建议分页查询
3. **权限控制**：在实际应用中需要添加用户权限验证
4. **缓存策略**：可以考虑对可见试卷列表进行缓存以提高性能