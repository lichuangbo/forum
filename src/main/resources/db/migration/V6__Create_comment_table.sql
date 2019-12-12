CREATE TABLE comment
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    parent_id bigint NOT NULL COMMENT '父类ID',
    type int NOT NULL COMMENT '父类类型',
    commentor int NOT NULL COMMENT '评论人ID',
    gmt_create bigint NOT NULL COMMENT '创建时间',
    gmt_modified bigint NOT NULL COMMENT '编辑时间',
    like_count bigint DEFAULT 0
);