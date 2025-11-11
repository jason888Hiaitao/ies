-- ----------------------------
-- 用户表
-- ----------------------------
CREATE TABLE et_user
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID，自增主键',
    username      VARCHAR(255) NOT NULL COMMENT '用户名',
    password      VARCHAR(255) NOT NULL COMMENT '用户密码',
    email         VARCHAR(255) COMMENT '用户邮箱',
    phone         VARCHAR(20) COMMENT '用户手机号',
    add_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '添加日期',
    expire_date   TIMESTAMP COMMENT '过期日期',
    add_by        VARCHAR(255) COMMENT '添加人',
    enabled       BOOLEAN COMMENT '是否启用',
    if_out_source BOOLEAN COMMENT '是否外包',
    truename      VARCHAR(255) COMMENT '真实姓名',
    field_id      BIGINT COMMENT '领域ID',
    province      VARCHAR(255) COMMENT '省份',
    company       VARCHAR(255) COMMENT '公司',
    department    VARCHAR(255) COMMENT '部门',
    groupname     VARCHAR(255) COMMENT '组名'
) COMMENT ='用户表';

-- ----------------------------
-- 试卷表
-- ----------------------------
CREATE TABLE et_exam_paper
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '试卷ID',
    name          VARCHAR(255) NOT NULL COMMENT '试卷名称',
    content       TEXT COMMENT '试卷内容',
    duration      INT COMMENT '考试时长（分钟）',
    pass_point    DECIMAL(5, 2) COMMENT '及格分数',
    total_point   DECIMAL(5, 2) COMMENT '总分数',
    status        VARCHAR(20) COMMENT '试卷状态',
    summary       TEXT COMMENT '试卷摘要',
    is_visible    BOOLEAN   DEFAULT TRUE COMMENT '是否可见',
    answer_sheet  TEXT COMMENT '答题卡内容',
    group_id      BIGINT COMMENT '所属分组ID',
    is_subjective BOOLEAN   DEFAULT FALSE COMMENT '是否主观题试卷',
    creator       VARCHAR(255) COMMENT '创建人',
    paper_type    VARCHAR(50) COMMENT '试卷类型',
    field_id      BIGINT COMMENT '所属领域ID',
    validsource   VARCHAR(255) COMMENT '有效来源',
    validdpt      VARCHAR(255) COMMENT '有效部门',
    exam_count    INT       DEFAULT 0 COMMENT '考试次数',
    answer_hide   BOOLEAN   DEFAULT FALSE COMMENT '答案是否隐藏',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT ='试卷表';

-- ----------------------------
-- 题目表
-- ----------------------------
CREATE TABLE et_question
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
    name             VARCHAR(255) COMMENT '题目名称',
    content          TEXT COMMENT '题目内容',
    question_type_id BIGINT COMMENT '题目类型ID',
    duration         INT COMMENT '答题时长',
    points           DECIMAL(10, 2) COMMENT '分值',
    group_id         BIGINT COMMENT '所属分组ID',
    is_visible       BOOLEAN COMMENT '是否可见',
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    creator          VARCHAR(255) COMMENT '创建人',
    last_modify      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    expose_times     INT       DEFAULT 0 COMMENT '曝光次数',
    right_times      INT       DEFAULT 0 COMMENT '答对次数',
    wrong_times      INT       DEFAULT 0 COMMENT '答错次数',
    difficulty       VARCHAR(50) COMMENT '难度',
    analysis         TEXT COMMENT '解析',
    reference        TEXT COMMENT '参考答案',
    examining_point  TEXT COMMENT '考查点',
    keyword          TEXT COMMENT '关键词'
) COMMENT ='试卷问题表';

-- ----------------------------
-- 练习试卷表
-- ----------------------------
CREATE TABLE et_practice_paper
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '练习试卷ID',
    name          VARCHAR(255) COMMENT '练习试卷名称',
    user_id       BIGINT COMMENT '用户ID',
    content       TEXT COMMENT '试卷内容',
    duration      INT COMMENT '考试时长（分钟）',
    pass_point    DECIMAL(5, 2) COMMENT '及格分数',
    total_point   DECIMAL(5, 2) COMMENT '总分数',
    status        VARCHAR(50) COMMENT '试卷状态',
    summary       TEXT COMMENT '试卷摘要',
    is_visible    BOOLEAN COMMENT '是否可见',
    answer_sheet  TEXT COMMENT '答题卡内容',
    group_id      BIGINT COMMENT '所属分组ID',
    is_subjective BOOLEAN COMMENT '是否主观题试卷',
    creator       VARCHAR(255) COMMENT '创建人',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT ='练习试卷表';

-- ----------------------------
-- 用户考试历史表
-- ----------------------------
CREATE TABLE et_user_exam_history
(
    histId        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '历史记录ID',
    user_id       BIGINT    NOT NULL COMMENT '用户ID',
    exam_paper_id BIGINT    NOT NULL COMMENT '试卷ID',
    content       TEXT COMMENT '考试内容快照',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '开始考试时间',
    answer_sheet  TEXT COMMENT '用户答题卡',
    duration      INT COMMENT '实际用时（秒）',
    submit_time   TIMESTAMP NULL COMMENT '提交时间',
    point_get     DECIMAL(5, 2) COMMENT '获得分数',
    FOREIGN KEY (user_id) REFERENCES et_user (id),
    FOREIGN KEY (exam_paper_id) REFERENCES et_exam_paper (id)
) COMMENT ='考试历史记录表';

-- ----------------------------
-- 评论表
-- ----------------------------
CREATE TABLE et_comment
(
    comment_id    BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    question_id   BIGINT       NOT NULL COMMENT '题目ID',
    comment_index INT          NOT NULL COMMENT '评论索引（用于排序）',
    user_id       BIGINT       NOT NULL COMMENT '用户ID',
    content_msg   VARCHAR(255) NOT NULL COMMENT '评论内容',
    quote_id      BIGINT COMMENT '引用的评论ID',
    re_id         BIGINT COMMENT '回复的评论ID',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES et_user (id),
    FOREIGN KEY (question_id) REFERENCES et_question (id),
    FOREIGN KEY (quote_id) REFERENCES et_comment (comment_id),
    FOREIGN KEY (re_id) REFERENCES et_comment (comment_id)
) COMMENT ='评论表';

-- ----------------------------
-- 用户群组表 (临时用户/批量导入用户)
-- ----------------------------
CREATE TABLE et_user_temporary
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '临时用户ID',
    username             VARCHAR(255) COMMENT '用户名',
    password             VARCHAR(255) COMMENT '用户密码',
    email                VARCHAR(255) COMMENT '用户邮箱',
    phone                VARCHAR(255) COMMENT '用户手机号',
    add_date             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '添加日期',
    expire_date          TIMESTAMP COMMENT '过期日期',
    add_by               VARCHAR(255) COMMENT '添加人',
    enabled              BOOLEAN COMMENT '是否启用',
    if_out_source        BOOLEAN COMMENT '是否外包',
    truename             VARCHAR(255) COMMENT '真实姓名',
    field_id             BIGINT COMMENT '领域ID',
    province             VARCHAR(255) COMMENT '省份',
    company              VARCHAR(255) COMMENT '公司',
    department           VARCHAR(255) COMMENT '部门',
    groupname            VARCHAR(255) COMMENT '组名',
    temporary_department VARCHAR(255) COMMENT '临时部门',
    temporary_groupname  VARCHAR(255) COMMENT '临时组名'
) COMMENT ='用户群组表';


-- 插入初始数据
INSERT INTO et_field (name, description, status, creator, validdpt, validsource)
VALUES ('计算机科学', '计算机科学相关题库', 'active', 'admin', 'IT部门', '系统'),
       ('数学', '数学相关题库', 'active', 'admin', '教务处', '系统'),
       ('英语', '英语相关题库', 'active', 'admin', '外语学院', '系统');

INSERT INTO et_knowledge_point (name, description, status, creator, validdpt, validsource)
VALUES ('Java基础', 'Java编程基础知识', 'active', 'admin', 'IT部门', '系统'),
       ('数据结构', '数据结构与算法', 'active', 'admin', 'IT部门', '系统'),
       ('数据库', '数据库相关知识点', 'active', 'admin', 'IT部门', '系统'),
       ('微积分', '高等数学微积分部分', 'active', 'admin', '教务处', '系统'),
       ('线性代数', '线性代数相关知识点', 'active', 'admin', '教务处', '系统'),
       ('词汇', '英语词汇学习', 'active', 'admin', '外语学院', '系统'),
       ('语法', '英语语法知识点', 'active', 'admin', '外语学院', '系统');