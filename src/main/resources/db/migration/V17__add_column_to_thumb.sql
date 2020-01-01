ALTER TABLE thumbup RENAME TO thumb;
ALTER TABLE thumb ADD type int NULL;
ALTER TABLE thumb CHANGE questionid typeid bigint(20) NOT NULL;