CREATE TABLE user_auths
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    identify_type varchar(20) NOT NULL,
    identifier VARCHAR(50) NOT NULL,
    credential varchar(36) NOT NULL,
    gmt_create bigint NOT NULL,
    gmt_modify bigint NOT NULL
);
ALTER TABLE user_auths COMMENT = '用户授权表';