ALTER TABLE user ADD follow_count int NULL;
ALTER TABLE user
  MODIFY COLUMN follow_count int AFTER descript;
ALTER TABLE follow DROP gmt_modify;