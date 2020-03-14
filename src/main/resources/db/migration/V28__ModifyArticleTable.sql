ALTER TABLE question RENAME TO article;
ALTER TABLE article ALTER COLUMN comment_count SET DEFAULT 0;
ALTER TABLE article ALTER COLUMN like_count SET DEFAULT 0;
ALTER TABLE article ALTER COLUMN view_count SET DEFAULT 0;
ALTER TABLE article CHANGE description content text;
ALTER TABLE article CHANGE creater author_id bigint(20);
ALTER TABLE article
  MODIFY COLUMN gmt_modified bigint(20) AFTER tag,
  MODIFY COLUMN gmt_create bigint(20) AFTER tag,
  MODIFY COLUMN comment_count int(11) DEFAULT 0 AFTER tag,
  MODIFY COLUMN author_id bigint(20) AFTER id,
  MODIFY COLUMN tag varchar(256) AFTER content;