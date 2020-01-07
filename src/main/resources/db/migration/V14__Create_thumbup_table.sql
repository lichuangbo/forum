create table thumbup
(
    id bigint primary key auto_increment,
    userid bigint not null,
    questionid bigint not null
);