ALTER TABLE user MODIFY id bigint NOT NULL auto_increment;
ALTER TABLE comment MODIFY commentor bigint NOT NULL COMMENT '???ID';
ALTER TABLE question MODIFY id BIGINT NOT NULL auto_increment;
ALTER TABLE question MODIFY creater bigint;