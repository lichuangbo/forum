CREATE TABLE follow
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    follow_user_id bigint(20) NOT NULL,
    gmt_create bigint(20) NOT NULL,
    gmt_modify bigint(20) NOT NULL
);