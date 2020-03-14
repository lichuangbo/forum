CREATE TABLE favorite
(
  id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL,
  article_id bigint(20) NOT NULL,
  gmt_create BIGINT(20) NOT NULL
);