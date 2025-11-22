# Mapper优化方案

## 概述

本文档描述了对项目中mapper目录下所有SQL查询语句的优化工作，重点处理了重复查询同一张表的情况，并为每张表统一实现了标准操作：带条件的新增数据、带条件的插入数据、带条件的查询以及带条件的分页查询功能。

## 优化目标

1. **消除重复查询**：合并多个mapper文件中对同一张表的重复查询
2. **统一标准操作**：为每张表实现统一的标准CRUD操作
3. **提高执行效率**：优化SQL语句，减少不必要的查询和数据处理
4. **增强可维护性**：统一命名规范和操作方式，便于后续维护

## 优化策略

### 1. 标准化操作

为每张表实现以下8种标准操作：

1. **带条件的新增数据** (`insertSelective`)
   - 只插入非空字段
   - 使用`<trim>`标签动态构建SQL

2. **带条件的插入数据** (`insertOrUpdate`)
   - 使用`ON DUPLICATE KEY UPDATE`语法
   - 实现插入或更新逻辑

3. **带条件的查询** (`selectByCondition`)
   - 支持多条件组合查询
   - 使用`<where>`标签动态构建WHERE子句

4. **带条件的分页查询** (`selectByConditionWithPage`)
   - 支持多条件组合查询
   - 支持分页和排序

5. **统计符合条件的记录数** (`countByCondition`)
   - 支持多条件组合统计
   - 返回记录总数

6. **批量插入** (`batchInsert`)
   - 使用`<foreach>`标签实现批量插入
   - 提高批量操作效率

7. **批量删除** (`batchDelete`)
   - 使用`<foreach>`标签实现批量删除
   - 提高批量操作效率

8. **带条件的更新** (`updateByCondition`)
   - 只更新非空字段
   - 使用`<set>`标签动态构建SET子句

### 2. SQL优化技巧

1. **使用`<sql>`标签**：提取公共SQL片段，减少重复代码
2. **使用`<trim>`标签**：动态构建SQL，避免无效字段
3. **使用`<where>`标签**：动态构建WHERE子句，避免多余的AND
4. **使用`<foreach>`标签**：高效处理批量操作
5. **合理使用索引**：确保查询条件使用适当的索引

## 优化结果

### 1. 统一Mapper文件列表

| 表名 | 原Mapper文件 | 优化后Mapper文件 | 主要优化点 |
|------|-------------|----------------|----------|
| et_comment | EtCommentMapper.xml | UnifiedEtCommentMapper.xml | 统一CRUD操作，增加条件查询 |
| et_exam_paper | EtExamPaperMapper.xml | UnifiedEtExamPaperMapper.xml | 统一CRUD操作，增加条件查询 |
| et_user_exam_history | EtUserExamHistoryMapper.xml, EtExamAnalysisMapper.xml | UnifiedEtUserExamHistoryMapper.xml, UnifiedEtExamAnalysisMapper.xml | 合并重复查询，统一CRUD操作 |
| et_question | EtQuestionMapper.xml | UnifiedEtQuestionMapper.xml | 统一CRUD操作，增加条件查询 |
| et_field | EtFieldMapper.xml | UnifiedEtFieldMapper.xml | 统一CRUD操作，增加条件查询 |
| et_knowledge_point | EtKnowledgePointMapper.xml | UnifiedEtKnowledgePointMapper.xml | 统一CRUD操作，增加条件查询 |
| et_user_group | EtUserGroupMapper.xml | UnifiedEtUserGroupMapper.xml | 统一CRUD操作，增加条件查询 |
| et_user_group_member | EtUserGroupMemberMapper.xml | UnifiedEtUserGroupMemberMapper.xml | 统一CRUD操作，增加条件查询 |
| et_user_temporary | EtUserTemporaryMapper.xml | UnifiedEtUserTemporaryMapper.xml | 统一CRUD操作，增加条件查询 |
| et_question_2_point | EtQuestion2PointMapper.xml | UnifiedEtQuestion2PointMapper.xml | 统一CRUD操作，增加条件查询 |
| et_exam_paper_question | EtExamPaperQuestionMapper.xml | UnifiedEtExamPaperQuestionMapper.xml | 统一CRUD操作，增加条件查询 |
| et_user | UserMapper.xml | UnifiedUserMapper.xml | 统一CRUD操作，增加条件查询 |

### 2. 主要优化点

#### 2.1 et_user_exam_history表优化

**问题**：EtUserExamHistoryMapper.xml和EtExamAnalysisMapper.xml中存在对et_user_exam_history表的重复查询

**解决方案**：
1. 合并两个mapper文件的功能到UnifiedEtUserExamHistoryMapper.xml
2. 将EtExamAnalysisMapper.xml中的统计和分析功能移到UnifiedEtExamAnalysisMapper.xml
3. 统一结果映射和基础列定义
4. 优化统计查询，减少重复计算

#### 2.2 条件查询优化

**优化前**：
```xml
<select id="selectByName" parameterType="java.lang.String" resultMap="EtQuestionResultMap">
    SELECT <include refid="Base_Column_List"/>
    FROM et_question
    WHERE name LIKE CONCAT('%', #{name}, '%')
    ORDER BY id DESC
</select>

<select id="selectByTypeId" parameterType="java.lang.Long" resultMap="EtQuestionResultMap">
    SELECT <include refid="Base_Column_List"/>
    FROM et_question
    WHERE question_type_id = #{questionTypeId}
    ORDER BY id DESC
</select>
```

**优化后**：
```xml
<select id="selectByCondition" resultMap="EtQuestionResultMap">
    SELECT <include refid="Base_Column_List"/>
    FROM et_question
    <where>
        <if test="name != null and name != ''">
            AND name LIKE CONCAT('%', #{name}, '%')
        </if>
        <if test="questionTypeId != null">
            AND question_type_id = #{questionTypeId}
        </if>
        <!-- 其他条件 -->
    </where>
    ORDER BY id DESC
</select>
```

#### 2.3 批量操作优化

**优化前**：
```xml
<insert id="insert" parameterType="com.example.wmsiescore.model.EtQuestion">
    INSERT INTO et_question (name, content, ...)
    VALUES (#{name}, #{content}, ...)
</insert>
```

**优化后**：
```xml
<insert id="insertSelective" parameterType="com.example.wmsiescore.model.EtQuestion" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO et_question
    <trim prefix="(" suffix=")" suffixOverrides=",">
        <if test="name != null">name,</if>
        <if test="content != null">content,</if>
        <!-- 其他字段 -->
    </trim>
    <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
        <if test="name != null">#{name},</if>
        <if test="content != null">#{content},</if>
        <!-- 其他字段值 -->
    </trim>
</insert>

<insert id="batchInsert" parameterType="java.util.List">
    INSERT INTO et_question (name, content, ...)
    VALUES
    <foreach collection="list" item="item" separator=",">
        (#{item.name}, #{item.content}, ...)
    </foreach>
</insert>
```

## 使用指南

### 1. 标准CRUD操作

#### 1.1 带条件的新增数据

```java
// 只插入非空字段
etQuestionMapper.insertSelective(question);

// 插入或更新（如果主键冲突则更新）
etQuestionMapper.insertOrUpdate(question);
```

#### 1.2 带条件的查询

```java
// 单条件查询
Question queryCondition = new Question();
queryCondition.setName("Java基础");
List<Question> questions = etQuestionMapper.selectByCondition(queryCondition);

// 多条件查询
queryCondition.setName("Java基础");
queryCondition.setDifficulty("简单");
queryCondition.setIsVisible(true);
List<Question> questions = etQuestionMapper.selectByCondition(queryCondition);
```

#### 1.3 带条件的分页查询

```java
// 分页查询
QueryRequest queryRequest = new QueryRequest();
queryRequest.setCondition(queryCondition);
queryRequest.setOffset(0);
queryRequest.setPageSize(10);
List<Question> questions = etQuestionMapper.selectByConditionWithPage(queryRequest);

// 获取总数
int total = etQuestionMapper.countByCondition(queryCondition);
```

#### 1.4 批量操作

```java
// 批量插入
List<Question> questions = Arrays.asList(question1, question2, question3);
etQuestionMapper.batchInsert(questions);

// 批量删除
List<Long> ids = Arrays.asList(1L, 2L, 3L);
etQuestionMapper.batchDelete(ids);
```

### 2. 兼容原有方法

为了保持向后兼容性，所有优化后的mapper文件都保留了原有的方法：

```java
// 原有方法仍然可用
etQuestionMapper.insert(question);
etQuestionMapper.selectById(1L);
etQuestionMapper.updateById(question);
etQuestionMapper.deleteById(1L);
```

## 性能优化建议

1. **索引优化**：确保常用查询字段有适当的索引
2. **批量操作**：尽量使用批量插入和批量删除，减少数据库交互次数
3. **分页查询**：避免一次性查询大量数据，使用分页查询
4. **条件查询**：合理使用查询条件，避免全表扫描
5. **结果集限制**：只查询必要的字段，避免查询大字段

## 后续优化建议

1. **缓存策略**：对频繁查询但不常变更的数据添加缓存
2. **读写分离**：考虑将读操作和写操作分离到不同数据库
3. **连接池优化**：优化数据库连接池配置
4. **SQL监控**：添加SQL执行监控，识别慢查询
5. **定期维护**：定期分析表结构，优化索引

## 总结

通过本次优化，我们实现了以下目标：

1. **消除重复查询**：合并了重复查询同一张表的情况，特别是et_user_exam_history表
2. **统一标准操作**：为每张表实现了8种标准操作，提高了代码复用性
3. **提高执行效率**：优化了SQL语句，减少了不必要的查询和数据处理
4. **增强可维护性**：统一了命名规范和操作方式，便于后续维护

优化后的mapper文件不仅保持了原有功能的完整性，还提供了更强大、更灵活的查询方式，为系统的性能提升和后续功能扩展奠定了良好基础。