create table norification
(
    id bigint primary key auto_increment,
    notifier bigint not null,
    receiver bigint not null,
    outer_id bigint not null,
    type int not null,
    gmt_create bigint not null,
    status int default 0
);