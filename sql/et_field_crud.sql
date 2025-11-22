-- =============================================
-- 题库领域表 (et_field) 增删改SQL语句
-- =============================================

-- 1. 插入领域记录
INSERT INTO et_field (field_name, memo, state) VALUES ('数学', '数学领域相关题目', 1);
INSERT INTO et_field (field_name, memo, state) VALUES ('物理', '物理领域相关题目', 1);
INSERT INTO et_field (field_name, memo, state) VALUES ('化学', '化学领域相关题目', 1);
INSERT INTO et_field (field_name, memo, state) VALUES ('生物', '生物领域相关题目', 1);

-- 2. 更新领域记录
UPDATE et_field 
SET field_name = '高等数学', 
    memo = '高等数学领域相关题目', 
    state = 1 
WHERE field_id = 1;

-- 3. 删除领域记录
DELETE FROM et_field WHERE field_id = 4;

-- 4. 批量删除领域记录
DELETE FROM et_field WHERE field_id IN (2, 3);

-- 5. 更新状态为废弃
UPDATE et_field SET state = 0 WHERE field_id = 1;

-- 6. 批量更新状态
UPDATE et_field SET state = 0 WHERE field_id IN (1, 2, 3);

-- 7. 根据条件更新
UPDATE et_field 
SET memo = '已更新备注信息' 
WHERE field_name LIKE '%数学%' AND state = 1;