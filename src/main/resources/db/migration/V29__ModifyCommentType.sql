ALTER TABLE comment DROP gmt_modified;
ALTER TABLE comment ALTER COLUMN comment_count SET DEFAULT 0;
ALTER TABLE comment ALTER COLUMN like_count SET DEFAULT 0;
ALTER TABLE comment MODIFY gmt_create bigint(20) NOT NULL;
ALTER TABLE comment MODIFY commentor bigint(20) NOT NULL;
ALTER TABLE comment MODIFY type int(11) NOT NULL;
ALTER TABLE comment MODIFY parent_id bigint(20) NOT NULL;
ALTER TABLE comment
  MODIFY COLUMN content varchar(1024) NOT NULL AFTER id;