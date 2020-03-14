ALTER TABLE comment ADD resp_user_id bigint(20) NULL;
             ALTER TABLE comment CHANGE commentor user_id bigint(20) NOT NULL;
             ALTER TABLE comment
             MODIFY COLUMN resp_user_id bigint(20) AFTER user_id;