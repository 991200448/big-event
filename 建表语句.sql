-- 创建 big_event 数据库，如果不存在的话
CREATE DATABASE IF NOT EXISTS big_event;

-- 使用 big_event 数据库
USE big_event;

-- 创建用户表
CREATE TABLE user
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT 'ID',
    username    VARCHAR(20)             NOT NULL COMMENT '用户名',
    password    VARCHAR(32)             NULL COMMENT '密码',
    nickname    VARCHAR(10) DEFAULT ''  NULL COMMENT '昵称',
    email       VARCHAR(128) DEFAULT '' NULL COMMENT '邮箱',
    user_pic    VARCHAR(128) DEFAULT '' NULL COMMENT '头像',
    create_time DATETIME                NOT NULL COMMENT '创建时间',
    update_time DATETIME                NOT NULL COMMENT '修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY username (username)
) COMMENT '用户表';

-- 创建分类表
CREATE TABLE category
(
    id             INT UNSIGNED AUTO_INCREMENT COMMENT 'ID',
    category_name  VARCHAR(32)  NOT NULL COMMENT '分类名称',
    category_alias VARCHAR(32)  NOT NULL COMMENT '分类别名',
    create_user    INT UNSIGNED NOT NULL COMMENT '创建人ID',
    create_time    DATETIME     NOT NULL COMMENT '创建时间',
    update_time    DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (id),
    FOREIGN KEY (create_user) REFERENCES user (id)
);

-- 创建文章表
CREATE TABLE article
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT 'ID',
    title       VARCHAR(30)               NOT NULL COMMENT '文章标题',
    content     VARCHAR(10000)            NOT NULL COMMENT '文章内容',
    cover_img   VARCHAR(128)              NOT NULL COMMENT '文章封面',
    state       VARCHAR(3) DEFAULT '草稿' NULL COMMENT '文章状态: 只能是[已发布] 或者 [草稿]',
    category_id INT UNSIGNED              NULL COMMENT '文章分类ID',
    create_user INT UNSIGNED              NOT NULL COMMENT '创建人ID',
    create_time DATETIME                  NOT NULL COMMENT '创建时间',
    update_time DATETIME                  NOT NULL COMMENT '修改时间',
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (create_user) REFERENCES user (id)
);
    