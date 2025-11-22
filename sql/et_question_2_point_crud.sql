-- =============================================
-- 试题与知识点关联表 (et_question_2_point) 增删改SQL语句
-- =============================================

-- 1. 插入关联记录
INSERT INTO et_question_2_point (question_id, point_id) VALUES (1001, 1);
INSERT INTO et_question_2_point (question_id, point_id) VALUES (1001, 2);
INSERT INTO et_question_2_point (question_id, point_id) VALUES (1002, 1);
INSERT INTO et_question_2_point (question_id, point_id) VALUES (1003, 3);
INSERT INTO et_question_2_point (question_id, point_id) VALUES (1004, 4);

-- 2. 更新关联记录
UPDATE et_question_2_point 
SET question_id = 1005, 
    point_id = 2 
WHERE question_2_point_id = 1;

-- 3. 删除关联记录
DELETE FROM et_question_2_point WHERE question_2_point_id = 5;

-- 4. 批量删除关联记录
DELETE FROM et_question_2_point WHERE question_2_point_id IN (3, 4);

-- 5. 根据试题ID删除关联记录
DELETE FROM et_question_2_point WHERE question_id = 1001;

-- 6. 根据知识点ID删除关联记录
DELETE FROM et_question_2_point WHERE point_id = 1;

-- 7. 根据试题ID批量删除关联记录
DELETE FROM et_question_2_point WHERE question_id IN (1001, 1002, 1003);

-- 8. 根据知识点ID批量删除关联记录
DELETE FROM et_question_2_point WHERE point_id IN (1, 2, 3);

-- 9. 批量插入关联记录
INSERT INTO et_question_2_point (question_id, point_id) VALUES 
(1006, 1),
(1006, 2),
(1007, 3),
(1008, 4),
(1009, 1);

-- 10. 为特定试题批量关联多个知识点
INSERT INTO et_question_2_point (question_id, point_id) 
SELECT 1010, point_id 
FROM et_knowledge_point 
WHERE field_id = 1 AND state = 1;

-- 11. 清空特定试题的所有知识点关联
DELETE FROM et_question_2_point WHERE question_id = 1010;

-- 12. 清空特定知识点的所有试题关联
DELETE FROM et_question_2_point WHERE point_id = 1;