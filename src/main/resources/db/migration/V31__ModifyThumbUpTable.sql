ALTER TABLE thumb RENAME TO thumb_up;
ALTER TABLE thumb_up ADD gmt_create bigint(20) NOT NULL;
ALTER TABLE thumb_up CHANGE userid user_id bigint(20) NOT NULL;
ALTER TABLE thumb_up CHANGE typeid target_id bigint(20) NOT NULL;
ALTER TABLE thumb_up MODIFY type int(11) NOT NULL;