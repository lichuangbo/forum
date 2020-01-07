alter table user modify id bigint not null auto_increment;
alter table comment modify commentor bigint not null comment '???id';
alter table question modify id bigint not null auto_increment;
alter table question modify creater bigint;