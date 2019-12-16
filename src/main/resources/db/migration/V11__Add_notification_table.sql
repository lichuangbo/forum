CREATE TABLE norification
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    notifier bigint NOT NULL,
    receiver bigint NOT NULL,
    outer_id bigint NOT NULL,
    type int NOT NULL,
    gmt_create bigint NOT NULL,
    status int DEFAULT 0
);