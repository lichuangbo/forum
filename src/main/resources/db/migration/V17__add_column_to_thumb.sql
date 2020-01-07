alter table thumbup rename to thumb;
alter table thumb add type int null;
alter table thumb change questionid typeid bigint(20) not null;