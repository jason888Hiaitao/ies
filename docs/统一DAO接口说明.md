# 统一DAO接口说明

## 概述

本文档说明了为优化后的Mapper XML文件生成的统一DAO接口。这些DAO接口提供了标准化的数据访问方法，与对应的Mapper XML文件中的SQL操作一一对应。

## 接口列表

### 1. UnifiedEtCommentDao
- **对应文件**: `UnifiedEtCommentMapper.xml`
- **功能**: 提供评论表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入评论记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新评论记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询评论列表
  - `selectByConditionWithPage` - 根据条件分页查询评论列表
  - `countByCondition` - 根据条件统计评论数量
  - `batchInsert` - 批量插入评论记录
  - `batchDelete` - 批量删除评论记录
  - `updateByCondition` - 根据条件更新评论记录（只更新非空字段）

### 2. UnifiedEtExamPaperDao
- **对应文件**: `UnifiedEtExamPaperMapper.xml`
- **功能**: 提供试卷表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入试卷记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新试卷记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询试卷列表
  - `selectByConditionWithPage` - 根据条件分页查询试卷列表
  - `countByCondition` - 根据条件统计试卷数量
  - `batchInsert` - 批量插入试卷记录
  - `batchDelete` - 批量删除试卷记录
  - `updateByCondition` - 根据条件更新试卷记录（只更新非空字段）

### 3. UnifiedEtUserExamHistoryDao
- **对应文件**: `UnifiedEtUserExamHistoryMapper.xml`
- **功能**: 提供用户考试历史表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入用户考试历史记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新用户考试历史记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询用户考试历史列表
  - `selectByConditionWithPage` - 根据条件分页查询用户考试历史列表
  - `countByCondition` - 根据条件统计用户考试历史数量
  - `batchInsert` - 批量插入用户考试历史记录
  - `batchDelete` - 批量删除用户考试历史记录
  - `updateByCondition` - 根据条件更新用户考试历史记录（只更新非空字段）
  - `selectByUserId` - 根据用户ID查询考试历史记录
  - `selectByExamPaperId` - 根据试卷ID查询考试历史记录
  - `selectByUserIdAndExamPaperId` - 根据用户ID和试卷ID查询考试历史记录

### 4. UnifiedEtQuestionDao
- **对应文件**: `UnifiedEtQuestionMapper.xml`
- **功能**: 提供试题表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入试题记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新试题记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询试题列表
  - `selectByConditionWithPage` - 根据条件分页查询试题列表
  - `countByCondition` - 根据条件统计试题数量
  - `batchInsert` - 批量插入试题记录
  - `batchDelete` - 批量删除试题记录
  - `updateByCondition` - 根据条件更新试题记录（只更新非空字段）
  - `selectByQuestionTypeId` - 根据题目类型ID查询试题记录
  - `selectByGroupId` - 根据分组ID查询试题记录
  - `selectByCreator` - 根据创建人查询试题记录
  - `selectByDifficulty` - 根据难度查询试题记录
  - `selectByIsVisible` - 根据是否可见查询试题记录
  - `selectByNameLike` - 根据名称模糊查询试题记录

### 5. UnifiedEtFieldDao
- **对应文件**: `UnifiedEtFieldMapper.xml`
- **功能**: 提供领域表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入领域记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新领域记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询领域列表
  - `selectByConditionWithPage` - 根据条件分页查询领域列表
  - `countByCondition` - 根据条件统计领域数量
  - `batchInsert` - 批量插入领域记录
  - `batchDelete` - 批量删除领域记录
  - `updateByCondition` - 根据条件更新领域记录（只更新非空字段）
  - `selectByName` - 根据领域名称查询领域记录
  - `selectByState` - 根据状态查询领域记录
  - `selectByNameLike` - 根据名称模糊查询领域记录

### 6. UnifiedEtKnowledgePointDao
- **对应文件**: `UnifiedEtKnowledgePointMapper.xml`
- **功能**: 提供知识点表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入知识点记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新知识点记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询知识点列表
  - `selectByConditionWithPage` - 根据条件分页查询知识点列表
  - `countByCondition` - 根据条件统计知识点数量
  - `batchInsert` - 批量插入知识点记录
  - `batchDelete` - 批量删除知识点记录
  - `updateByCondition` - 根据条件更新知识点记录（只更新非空字段）
  - `selectByName` - 根据知识点名称查询知识点记录
  - `selectByFieldId` - 根据领域ID查询知识点记录
  - `selectByState` - 根据状态查询知识点记录
  - `selectByNameLike` - 根据名称模糊查询知识点记录

### 7. UnifiedEtUserGroupDao
- **对应文件**: `UnifiedEtUserGroupMapper.xml`
- **功能**: 提供用户组表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入用户组记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新用户组记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询用户组列表
  - `selectByConditionWithPage` - 根据条件分页查询用户组列表
  - `countByCondition` - 根据条件统计用户组数量
  - `batchInsert` - 批量插入用户组记录
  - `batchDelete` - 批量删除用户组记录
  - `updateByCondition` - 根据条件更新用户组记录（只更新非空字段）
  - `selectByName` - 根据用户组名称查询用户组记录
  - `selectByCreator` - 根据创建人查询用户组记录
  - `selectByNameLike` - 根据名称模糊查询用户组记录

### 8. UnifiedEtUserGroupMemberDao
- **对应文件**: `UnifiedEtUserGroupMemberMapper.xml`
- **功能**: 提供用户组成员表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入用户组成员记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新用户组成员记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询用户组成员列表
  - `selectByConditionWithPage` - 根据条件分页查询用户组成员列表
  - `countByCondition` - 根据条件统计用户组成员数量
  - `batchInsert` - 批量插入用户组成员记录
  - `batchDelete` - 批量删除用户组成员记录
  - `updateByCondition` - 根据条件更新用户组成员记录（只更新非空字段）
  - `selectByUserGroupId` - 根据用户组ID查询用户组成员记录
  - `selectByUserId` - 根据用户ID查询用户组成员记录
  - `selectByRole` - 根据角色查询用户组成员记录
  - `selectByUserGroupIdAndUserId` - 根据用户组ID和用户ID查询用户组成员记录

### 9. UnifiedEtUserTemporaryDao
- **对应文件**: `UnifiedEtUserTemporaryMapper.xml`
- **功能**: 提供临时用户表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入临时用户记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新临时用户记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询临时用户列表
  - `selectByConditionWithPage` - 根据条件分页查询临时用户列表
  - `countByCondition` - 根据条件统计临时用户数量
  - `batchInsert` - 批量插入临时用户记录
  - `batchDelete` - 批量删除临时用户记录
  - `updateByCondition` - 根据条件更新临时用户记录（只更新非空字段）
  - `selectByUsername` - 根据用户名查询临时用户记录
  - `selectByEmail` - 根据邮箱查询临时用户记录
  - `selectByPhone` - 根据手机号查询临时用户记录
  - `selectByEnabled` - 根据是否启用查询临时用户记录
  - `selectByIfOutSource` - 根据是否外包查询临时用户记录
  - `selectByFieldId` - 根据领域ID查询临时用户记录
  - `selectByCompany` - 根据公司查询临时用户记录
  - `selectByDepartment` - 根据部门查询临时用户记录
  - `selectByGroupname` - 根据组名查询临时用户记录
  - `selectByTruenameLike` - 根据真实姓名模糊查询临时用户记录

### 10. UnifiedEtQuestion2PointDao
- **对应文件**: `UnifiedEtQuestion2PointMapper.xml`
- **功能**: 提供试题知识点关联表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入试题知识点关联记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新试题知识点关联记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询试题知识点关联列表
  - `selectByConditionWithPage` - 根据条件分页查询试题知识点关联列表
  - `countByCondition` - 根据条件统计试题知识点关联数量
  - `batchInsert` - 批量插入试题知识点关联记录
  - `batchDelete` - 批量删除试题知识点关联记录
  - `updateByCondition` - 根据条件更新试题知识点关联记录（只更新非空字段）
  - `selectByQuestionId` - 根据试题ID查询试题知识点关联记录
  - `selectByPointId` - 根据知识点ID查询试题知识点关联记录
  - `selectByQuestionIdAndPointId` - 根据试题ID和知识点ID查询试题知识点关联记录
  - `selectByQuestionIds` - 根据试题ID列表查询试题知识点关联记录
  - `selectByPointIds` - 根据知识点ID列表查询试题知识点关联记录
  - `deleteByQuestionId` - 根据试题ID删除试题知识点关联记录
  - `deleteByPointId` - 根据知识点ID删除试题知识点关联记录

### 11. UnifiedEtExamPaperQuestionDao
- **对应文件**: `UnifiedEtExamPaperQuestionMapper.xml`
- **功能**: 提供试卷试题关联表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入试卷试题关联记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新试卷试题关联记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询试卷试题关联列表
  - `selectByConditionWithPage` - 根据条件分页查询试卷试题关联列表
  - `countByCondition` - 根据条件统计试卷试题关联数量
  - `batchInsert` - 批量插入试卷试题关联记录
  - `batchDelete` - 批量删除试卷试题关联记录
  - `updateByCondition` - 根据条件更新试卷试题关联记录（只更新非空字段）
  - `selectByExamPaperId` - 根据试卷ID查询试卷试题关联记录
  - `selectByQuestionId` - 根据试题ID查询试卷试题关联记录
  - `selectByExamPaperIdAndQuestionId` - 根据试卷ID和试题ID查询试卷试题关联记录
  - `selectByExamPaperIdOrderByQuestionOrder` - 根据试卷ID查询试卷试题关联记录（按试题顺序排序）
  - `deleteByExamPaperId` - 根据试卷ID删除试卷试题关联记录
  - `deleteByQuestionId` - 根据试题ID删除试卷试题关联记录
  - `countQuestionsByExamPaperId` - 根据试卷ID统计试题数量
  - `sumPointsByExamPaperId` - 根据试卷ID计算总分

### 12. UnifiedUserDao
- **对应文件**: `UnifiedUserMapper.xml`
- **功能**: 提供用户表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入用户记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新用户记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询用户列表
  - `selectByConditionWithPage` - 根据条件分页查询用户列表
  - `countByCondition` - 根据条件统计用户数量
  - `batchInsert` - 批量插入用户记录
  - `batchDelete` - 批量删除用户记录
  - `updateByCondition` - 根据条件更新用户记录（只更新非空字段）
  - `selectByUsername` - 根据用户名查询用户记录
  - `selectByEmail` - 根据邮箱查询用户记录
  - `selectByPhone` - 根据手机号查询用户记录
  - `selectByEnabled` - 根据是否启用查询用户记录
  - `selectByIfOutSource` - 根据是否外包查询用户记录
  - `selectByFieldId` - 根据领域ID查询用户记录
  - `selectByCompany` - 根据公司查询用户记录
  - `selectByDepartment` - 根据部门查询用户记录
  - `selectByGroupname` - 根据组名查询用户记录
  - `selectByTruenameLike` - 根据真实姓名模糊查询用户记录
  - `selectByUsernameLike` - 根据用户名模糊查询用户记录

### 13. UnifiedEtExamAnalysisDao
- **对应文件**: `UnifiedEtExamAnalysisMapper.xml`
- **功能**: 提供考试分析表的标准CRUD操作和高级查询功能
- **主要方法**:
  - `insertSelective` - 插入考试分析记录（只插入非空字段）
  - `insertOrUpdate` - 插入或更新考试分析记录（使用ON DUPLICATE KEY UPDATE）
  - `selectByCondition` - 根据条件查询考试分析列表
  - `selectByConditionWithPage` - 根据条件分页查询考试分析列表
  - `countByCondition` - 根据条件统计考试分析数量
  - `batchInsert` - 批量插入考试分析记录
  - `batchDelete` - 批量删除考试分析记录
  - `updateByCondition` - 根据条件更新考试分析记录（只更新非空字段）
  - `selectByExamPaperId` - 根据试卷ID查询考试分析记录
  - `selectByUserId` - 根据用户ID查询考试分析记录
  - `selectByExamPaperIdAndUserId` - 根据试卷ID和用户ID查询考试分析记录
  - `selectByPassStatus` - 根据及格状态查询考试分析记录
  - `avgScoreByExamPaperId` - 统计试卷的平均分
  - `maxScoreByExamPaperId` - 统计试卷的最高分
  - `minScoreByExamPaperId` - 统计试卷的最低分
  - `passRateByExamPaperId` - 统计试卷的及格率
  - `countByExamPaperId` - 统计试卷的考试人数
  - `countByUserId` - 统计用户的考试次数
  - `avgScoreByUserId` - 统计用户的平均分
  - `passRateByUserId` - 统计用户的及格率
  - `scoreDistributionByExamPaperId` - 按分数段统计试卷考试人数
  - `dailyCountByExamPaperId` - 按日期统计试卷考试人数

## 标准方法说明

所有统一DAO接口都实现了以下标准方法：

1. **insertSelective** - 插入记录（只插入非空字段）
2. **insertOrUpdate** - 插入或更新记录（使用ON DUPLICATE KEY UPDATE）
3. **selectByCondition** - 根据条件查询记录列表
4. **selectByConditionWithPage** - 根据条件分页查询记录列表
5. **countByCondition** - 根据条件统计记录数量
6. **batchInsert** - 批量插入记录
7. **batchDelete** - 批量删除记录
8. **updateByCondition** - 根据条件更新记录（只更新非空字段）
9. **selectById** - 根据ID查询记录
10. **deleteById** - 根据ID删除记录
11. **updateById** - 根据ID更新记录
12. **selectAll** - 查询所有记录

## 使用示例

```java
@Autowired
private UnifiedEtQuestionDao unifiedEtQuestionDao;

// 插入试题
EtQuestion question = new EtQuestion();
question.setName("测试题目");
question.setContent("这是一道测试题目");
question.setDifficulty("简单");
int result = unifiedEtQuestionDao.insertSelective(question);

// 根据条件查询试题
List<EtQuestion> questions = unifiedEtQuestionDao.selectByCondition(
    null, // id
    "测试", // name
    null, // questionTypeId
    null, // duration
    null, // points
    null, // groupId
    true, // isVisible
    null, // creator
    null, // exposeTimes
    null, // rightTimes
    null, // wrongTimes
    "简单" // difficulty
);

// 分页查询试题
List<EtQuestion> pageQuestions = unifiedEtQuestionDao.selectByConditionWithPage(
    null, // id
    null, // name
    null, // questionTypeId
    null, // duration
    null, // points
    null, // groupId
    true, // isVisible
    null, // creator
    null, // exposeTimes
    null, // rightTimes
    null, // wrongTimes
    null, // difficulty
    0, // offset
    10 // pageSize
);

// 批量插入试题
List<EtQuestion> questions = new ArrayList<>();
// 添加试题到列表...
int batchResult = unifiedEtQuestionDao.batchInsert(questions);
```

## 注意事项

1. 所有DAO接口都使用`@Mapper`注解，确保Spring能够扫描并注册为Bean
2. 方法参数使用`@Param`注解指定参数名称，与Mapper XML中的参数名保持一致
3. 所有方法都添加了详细的JavaDoc注释，说明方法的用途和参数含义
4. 条件查询方法支持多条件组合，所有条件参数都是可选的
5. 分页查询方法使用offset和pageSize参数实现分页功能
6. 批量操作方法使用List参数，支持批量插入和删除
7. 更新操作方法只更新非空字段，避免覆盖已有数据

## 后续优化建议

1. 可以考虑添加更多的统计方法，如按时间范围统计、按条件分组统计等
2. 可以考虑添加缓存支持，提高查询性能
3. 可以考虑添加事务支持，确保数据一致性
4. 可以考虑添加动态SQL支持，提供更灵活的查询方式