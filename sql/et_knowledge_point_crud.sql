-- =============================================
-- 知识点表 (et_knowledge_point) 增删改SQL语句
-- =============================================

-- 1. 插入知识点记录
INSERT INTO et_knowledge_point (point_name, field_id, memo, state) VALUES ('代数', 1, '代数相关知识点', 1);
INSERT INTO et_knowledge_point (point_name, field_id, memo, state) VALUES ('几何', 1, '几何相关知识点', 1);
INSERT INTO et_knowledge_point (point_name, field_id, memo, state) VALUES ('力学', 2, '力学相关知识点', 1);
INSERT INTO et_knowledge_point (point_name, field_id, memo, state) VALUES ('电磁学', 2, '电磁学相关知识点', 1);
INSERT INTO et_knowledge_point (point_name, field_id, memo, state) VALUES ('有机化学', 3, '有机化学相关知识点', 1);

-- 2. 更新知识点记录
UPDATE et_knowledge_point 
SET point_name = '线性代数', 
    field_id = 1, 
    memo = '线性代数相关知识点', 
    state = 1 
WHERE point_id = 1;

-- 3. 删除知识点记录
DELETE FROM et_knowledge_point WHERE point_id = 5;

-- 4. 批量删除知识点记录
DELETE FROM et_knowledge_point WHERE point_id IN (3, 4);

-- 5. 根据领域ID删除知识点记录
DELETE FROM et_knowledge_point WHERE field_id = 2;

-- 6. 更新状态为废弃
UPDATE et_knowledge_point SET state = 0 WHERE point_id = 1;

-- 7. 批量更新状态
UPDATE et_knowledge_point SET state = 0 WHERE point_id IN (1, 2);

-- 8. 根据领域ID批量更新状态
UPDATE et_knowledge_point SET state = 0 WHERE field_id = 1;

-- 9. 根据条件更新
UPDATE et_knowledge_point 
SET memo = '已更新备注信息' 
WHERE point_name LIKE '%代数%' AND state = 1;

-- 10. 批量插入知识点记录
INSERT INTO et_knowledge_point (point_name, field_id, memo, state) VALUES 
('微积分', 1, '微积分相关知识点', 1),
('概率论', 1, '概率论相关知识点', 1),
('统计学', 1, '统计学相关知识点', 1);