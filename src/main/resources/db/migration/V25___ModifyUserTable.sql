ALTER TABLE user ADD email_password varchar(20) NULL;
ALTER TABLE user ALTER COLUMN descript SET DEFAULT '暂无描述';
ALTER TABLE user ADD email varchar(20) DEFAULT '暂无' NULL;
ALTER TABLE user
  MODIFY COLUMN email varchar(20) DEFAULT '暂无' AFTER nickname,
  MODIFY COLUMN gmt_modify bigint(20) NOT NULL AFTER email_password,
  MODIFY COLUMN credential varchar(36) NOT NULL AFTER identify_type,
  MODIFY COLUMN gmt_create bigint(20) NOT NULL AFTER email_password,
  MODIFY COLUMN identifier varchar(50) NOT NULL AFTER identify_type;