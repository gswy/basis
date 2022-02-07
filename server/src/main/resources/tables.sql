-- 后台管理员表 --
DROP TABLE IF EXISTS `admin_users`;
CREATE TABLE `admin_users` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `username` varchar(191) NOT NULL COMMENT '用户名',
    `password` varchar(191) NOT NULL COMMENT '用户密码',
    `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态',
    `created_at` datetime NOT NULL COMMENT '创建时间',
    `updated_at` datetime NOT NULL COMMENT '修改时间',
    `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    KEY (`created_at`, `updated_at`, `deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '后台管理员表';

-- RBAC 后台角色表 --
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `name` varchar(191) NOT NULL COMMENT '角色名',
    `guard` varchar(191) NOT NULL COMMENT '守卫组',
    `created_at` datetime NOT NULL COMMENT '创建时间',
    `updated_at` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY (`guard`, `created_at`, `updated_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '全局角色表';

-- RBAC 后台权限表 --
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `name` varchar(191) NOT NULL COMMENT '用户名',
    `parent_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '上级权限id',
    `guard` varchar(191) NOT NULL COMMENT '守卫组',
    `created_at` datetime NOT NULL COMMENT '创建时间',
    `updated_at` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY (`name`, `parent_id`, `guard`, `created_at`, `updated_at`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '全局权限表';

-- RBAC 后台管理员拥有的角色表关联 --
DROP TABLE IF EXISTS `users_has_roles`;
CREATE TABLE `users_has_roles` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `user_id` bigint(20) unsigned NOT NULL COMMENT '管理员ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`, `user_id`, `role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '全局用户拥有的角色表关联';

-- RBAC 后台角色拥有的权限表关联 --
DROP TABLE IF EXISTS `roles_has_permissions`;
CREATE TABLE `roles_has_permissions` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`, `role_id`, `permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '全局角色拥有的权限表关联';

-- 后台管理员操作日志表 --
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `guard` varchar(191) NOT NULL COMMENT '守卫组',
    `user_id` bigint(20) unsigned NOT NULL COMMENT '管理员ID',
    `description` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作内容',
    `created_at` datetime NOT NULL COMMENT '操作时间',
    `deleted_at` datetime NULL COMMENT '软删除时间',
    PRIMARY KEY (`id`),
    KEY (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '后台用户操作日志表';