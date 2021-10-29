CREATE TABLE IF NOT EXISTS `role_permission`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT,
    `role_id` int(0) NOT NULL,
    `permission_id` int(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;