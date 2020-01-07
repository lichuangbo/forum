create table comment
(
    id bigint primary key auto_increment,
    parent_id bigint not null comment '父类id',
    type int not null comment '父类类型',
    commentor int not null comment '评论人id',
    gmt_create bigint not null comment '创建时间',
    gmt_modified bigint not null comment '编辑时间',
    like_count bigint default 0
);