create table question
(
    id int primary key auto_increment,
    title varchar(50),
    description text,
    gmt_create bigint,
    gmt_modified bigint,
    creater int,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256)
);