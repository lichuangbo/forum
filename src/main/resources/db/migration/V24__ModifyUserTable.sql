ALTER TABLE user ADD credential varchar(36) NOT NULL;
ALTER TABLE user ADD identifier varchar(50) NOT NULL;
ALTER TABLE user ADD identify_type varchar(20) NOT NULL;
ALTER TABLE user MODIFY id bigint NOT NULL auto_increment;
ALTER TABLE user
  MODIFY COLUMN gmt_create bigint(20) NOT NULL AFTER credential,
  MODIFY COLUMN gmt_modify bigint(20) NOT NULL AFTER credential,
  MODIFY COLUMN avatar_url varchar(200) AFTER credential,
  MODIFY COLUMN descript varchar(256) AFTER credential;
ALTER TABLE user COMMENT = '用户表';
drop table user_auths;