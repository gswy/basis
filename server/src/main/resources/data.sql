-- 角色表 --
INSERT INTO roles (id, name, guard, created_at, updated_at) VALUES (1, '超级管理员', 'admin', '2021-10-01 00:00:00', '2021-10-01 00:00:00');

-- 权限表 --
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (1, 0, 'admin', '管理员管理', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (2, 1, 'admin', '管理员列表', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (3, 1, 'admin', '创建管理员', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (4, 1, 'admin', '查看管理员', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (5, 1, 'admin', '修改管理员', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (6, 1, 'admin', '删除管理员', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (7, 1, 'admin', '分配角色', '2021-10-01 00:00:00', '2021-10-01 00:00:00');

INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (11, 1, 'admin', '角色列表', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (12, 1, 'admin', '创建角色', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (13, 1, 'admin', '查看角色', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (14, 1, 'admin', '修改角色', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (15, 1, 'admin', '删除角色', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (16, 1, 'admin', '角色下拉', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (17, 1, 'admin', '分配权限', '2021-10-01 00:00:00', '2021-10-01 00:00:00');

INSERT INTO permissions (id, parent_id, guard, name, created_at, updated_at) VALUES (20, 1, 'admin', '权限树', '2021-10-01 00:00:00', '2021-10-01 00:00:00');
