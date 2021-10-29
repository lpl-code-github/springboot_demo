CREATE TABLE if not exists `user_role`
(
    `id`      bigint(0) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(0) NOT NULL,
    `role_id` int(0)    NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;