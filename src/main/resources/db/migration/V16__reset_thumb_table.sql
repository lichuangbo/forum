ALTER TABLE thumb DROP type;
ALTER TABLE thumb RENAME TO thumbup;
ALTER TABLE thumbup CHANGE type_id questionid bigint(20) NOT NULL;