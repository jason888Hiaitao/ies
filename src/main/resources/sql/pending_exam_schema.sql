-- 待考试列表功能相关表结构更新
-- 涉及表：et_user, et_exam_paper, et_user_exam_history, et_user_temporary

-- 1. et_exam_paper 表新增字段（如果不存在）
ALTER TABLE et_exam_paper 
ADD COLUMN IF NOT EXISTS is_visible INT DEFAULT 1 COMMENT '是否可见：0-不可见，1-可见';

ALTER TABLE et_exam_paper 
ADD COLUMN IF NOT EXISTS paper_status INT DEFAULT 1 COMMENT '试卷状态：0-禁用，1-启用';

ALTER TABLE et_exam_paper 
ADD COLUMN IF NOT EXISTS valid_dpt VARCHAR(500) COMMENT '有效部门（多个部门用逗号分隔）';

ALTER TABLE et_exam_paper 
ADD COLUMN IF NOT EXISTS valid_source VARCHAR(500) COMMENT '有效群组（多个群组用逗号分隔）';

-- 2. et_user_exam_history 表新增字段（如果不存在）
ALTER TABLE et_user_exam_history 
ADD COLUMN IF NOT EXISTS submit_time TIMESTAMP NULL COMMENT '提交时间';

-- 3. 创建索引以提高查询性能
CREATE INDEX IF NOT EXISTS idx_exam_paper_visible ON et_exam_paper(is_visible, paper_status);
CREATE INDEX IF NOT EXISTS idx_exam_paper_valid_dpt ON et_exam_paper(valid_dpt);
CREATE INDEX IF NOT EXISTS idx_exam_paper_valid_source ON et_exam_paper(valid_source);
CREATE INDEX IF NOT EXISTS idx_user_exam_history_submit ON et_user_exam_history(user_id, submit_time);
CREATE INDEX IF NOT EXISTS idx_user_department ON et_user(department);
CREATE INDEX IF NOT EXISTS idx_user_groupname ON et_user(groupname);
CREATE INDEX IF NOT EXISTS idx_user_temporary_group ON et_user_temporary(temporary_groupname);

-- 4. 示例数据插入（可选）
-- 插入测试用户
INSERT INTO et_user (id, username, truename, department, groupname, enabled, add_date) VALUES 
(1, 'zhangsan', '张三', '技术部', '开发组', 1, NOW()),
(2, 'lisi', '李四', '技术部', '测试组', 1, NOW()),
(3, 'wangwu', '王五', '市场部', '营销组', 1, NOW())
ON DUPLICATE KEY UPDATE username = VALUES(username);

-- 插入临时用户群组
INSERT INTO et_user_temporary (id, username, temporary_groupname, enabled, add_date) VALUES 
(1, 'zhangsan', '临时开发组', 1, NOW()),
(2, 'lisi', '临时测试组', 1, NOW())
ON DUPLICATE KEY UPDATE username = VALUES(username);

-- 插入测试试卷
INSERT INTO et_exam_paper (id, title, description, is_visible, paper_status, valid_dpt, valid_source, created_by, create_time) VALUES 
(1, 'Java基础考试', 'Java基础知识测试', 1, 1, '技术部', '开发组,临时开发组', 1, NOW()),
(2, '测试技能考试', '软件测试相关知识', 1, 1, '技术部', '测试组,临时测试组', 1, NOW()),
(3, '市场营销考试', '市场营销基础知识', 1, 1, '市场部', '营销组', 1, NOW()),
(4, '全公司通用考试', '公司规章制度考试', 1, 1, '技术部,市场部', '开发组,测试组,营销组', 1, NOW())
ON DUPLICATE KEY UPDATE title = VALUES(title);

-- 插入考试历史记录（部分已完成，部分未完成）
INSERT INTO et_user_exam_history (id, user_id, exam_paper_id, status, submit_time, create_time) VALUES 
(1, 1, 1, 'completed', NOW(), NOW()),  -- 张三已完成Java基础考试
(2, 1, 2, 'incomplete', NULL, NOW()),   -- 张三未完成测试技能考试
(3, 2, 2, 'completed', NOW(), NOW()),  -- 李四已完成测试技能考试
(4, 3, 3, 'completed', NOW(), NOW())   -- 王五已完成市场营销考试
ON DUPLICATE KEY UPDATE status = VALUES(status);