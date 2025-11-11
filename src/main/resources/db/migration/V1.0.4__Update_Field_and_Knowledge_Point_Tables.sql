-- 删除旧的表（如果存在）
DROP TABLE IF EXISTS et_knowledge_point;
DROP TABLE IF EXISTS et_field;

-- 创建题库领域表
CREATE TABLE `et_field`
(
    `field_id`   int                                                          NOT NULL AUTO_INCREMENT COMMENT '领域ID，主键自增',
    `field_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '领域名称',
    `memo`       varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         DEFAULT NULL COMMENT '备注信息',
    `state`      decimal(1, 0)                                                NOT NULL DEFAULT '1' COMMENT '状态：1-正常 0-废弃',
    PRIMARY KEY (`field_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb3
  ROW_FORMAT = DYNAMIC COMMENT ='题库领域表，存储题目所属的学科领域信息';

-- 创建知识点表
CREATE TABLE `et_knowledge_point`
(
    `point_id`   int                                                           NOT NULL AUTO_INCREMENT COMMENT '知识点ID，主键自增',
    `point_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '知识点名称',
    `field_id`   int                                                           NOT NULL COMMENT '所属领域ID，外键关联et_field.field_id',
    `memo`       varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注信息',
    `state`      decimal(1, 0)                                                 DEFAULT '1' COMMENT '状态：1-正常 0-废弃',
    PRIMARY KEY (`point_id`),
    KEY `fk_knowledge_field` (`field_id`),
    CONSTRAINT `fk_knowledge_field` FOREIGN KEY (`field_id`) REFERENCES `et_field` (`field_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb3
  ROW_FORMAT = DYNAMIC COMMENT ='知识点表，存储各领域下的具体知识点';

# --试题与知识点关联表
CREATE TABLE `et_question_2_point`
(
    `question_2_point_id` int NOT NULL AUTO_INCREMENT COMMENT '关联ID，主键自增',
    `question_id`         int DEFAULT NULL COMMENT '试题ID，关联试题表',
    `point_id`            int DEFAULT NULL COMMENT '知识点ID，外键关联et_knowledge_point.point_id',
    PRIMARY KEY (`question_2_point_id`),
    KEY `fk_question_111` (`question_id`),
    KEY `fk_point_111` (`point_id`),
    CONSTRAINT `fk_point_111` FOREIGN KEY (`point_id`) REFERENCES `et_knowledge_point` (`point_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4384
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='试题与知识点关联表，建立题目与知识点的多对多关系';

-- 插入初始数据
INSERT INTO et_field (field_name, memo, state)
VALUES ('计算机科学', '计算机科学相关题库', 1),
       ('数学', '数学相关题库', 1),
       ('英语', '英语相关题库', 1),
       ('物理', '物理相关题库', 1),
       ('化学', '化学相关题库', 1);

INSERT INTO et_knowledge_point (point_name, field_id, memo, state)
VALUES
-- 计算机科学知识点
('Java基础', 1, 'Java编程基础知识', 1),
('数据结构', 1, '数据结构与算法', 1),
('数据库', 1, '数据库相关知识点', 1),
('计算机网络', 1, '计算机网络基础知识', 1),
('操作系统', 1, '操作系统原理', 1),
-- 数学知识点
('微积分', 2, '高等数学微积分部分', 1),
('线性代数', 2, '线性代数相关知识点', 1),
('概率论', 2, '概率论与数理统计', 1),
('离散数学', 2, '离散数学基础', 1),
-- 英语知识点
('词汇', 3, '英语词汇学习', 1),
('语法', 3, '英语语法知识点', 1),
('阅读理解', 3, '英语阅读理解技巧', 1),
('写作', 3, '英语写作技巧', 1),
-- 物理知识点
('力学', 4, '经典力学基础', 1),
('电磁学', 4, '电磁学基础知识', 1),
('热力学', 4, '热力学与统计物理', 1),
('光学', 4, '光学基础知识', 1),
-- 化学知识点
('无机化学', 5, '无机化学基础知识', 1),
('有机化学', 5, '有机化学基础知识', 1),
('分析化学', 5, '分析化学方法', 1),
('物理化学', 5, '物理化学原理', 1);