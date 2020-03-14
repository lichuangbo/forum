ALTER TABLE user CHANGE name nickname varchar(50);
ALTER TABLE user CHANGE bio descript varchar(256);
ALTER TABLE user DROP account_id;
ALTER TABLE user DROP token;
ALTER TABLE user DROP gmt_create;
ALTER TABLE user DROP gmt_modified;