ALTER TABLE user MODIFY id varchar(36) NOT NULL;
ALTER TABLE user COMMENT = '用户表';
ALTER TABLE user ADD gmt_create bigint NOT NULL;
ALTER TABLE user ADD gmt_modify bigint NOT NULL;
ALTER TABLE user_auths MODIFY user_id varchar(36) NOT NULL;
ALTER TABLE user_auths DROP gmt_create;
ALTER TABLE user_auths DROP gmt_modify;