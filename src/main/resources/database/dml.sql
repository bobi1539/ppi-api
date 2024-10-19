INSERT INTO public.m_system_parameter
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, description, "name")
VALUES('2024-10-15 21:35:35.535', 1, 'Super Admin', false, '2024-10-15 21:35:35.535', 1, 'Super Admin', '', 'GENDER');


INSERT INTO public.m_system_parameter_list
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", system_parameter_id)
VALUES('2024-10-15 21:36:02.878', 1, 'Super Admin', false, '2024-10-15 21:36:23.425', 1, 'Super Admin', 'Man', 1);
INSERT INTO public.m_system_parameter_list
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", system_parameter_id)
VALUES('2024-10-15 21:36:08.581', 1, 'Super Admin', false, '2024-10-15 21:36:28.907', 1, 'Super Admin', 'Woman', 1);


INSERT INTO public.m_user_role
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name")
VALUES('2024-10-15 21:32:24.400', 1, 'Super Admin', false, '2024-10-15 21:32:24.400', 1, 'Super Admin', 'Super Admin');


INSERT INTO public.m_user
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, description, email, email_verified_at, is_active, name, "password", photo, username, user_role_id)
VALUES('2024-10-15 21:32:24.400', 1, 'Super Admin', false, '2024-10-15 21:32:24.400', 1, 'Super Admin', 'User Super admin', 'superadmin@gmail.com', '2024-10-15 21:32:24.400', true, 'Super Admin', '$2a$12$pti4QW.pJf1HjDODD55RGuKjShUYtcs08qroBbtFrPGN1ZT1gjrVq', NULL, 'superadmin', 1);


INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:38:45.262', 1, 'Super Admin', false, '2024-10-15 21:38:45.262', 1, 'Super Admin', 'fa-solid fa-gauge-simple', 'Dashboard', '/office/dashboard', 1);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:39:57.034', 1, 'Super Admin', false, '2024-10-15 21:39:57.034', 1, 'Super Admin', 'fa-solid fa-users-gear', 'User', '/office/user', 2);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:40:40.507', 1, 'Super Admin', false, '2024-10-15 21:40:40.507', 1, 'Super Admin', 'fa-solid fa-users', 'Committee', '/office/committee', 3);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:42:22.138', 1, 'Super Admin', false, '2024-10-15 21:42:22.138', 1, 'Super Admin', 'fa-solid fa-newspaper', 'Newsletter', '/office/newsletter', 4);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:43:02.259', 1, 'Super Admin', false, '2024-10-15 21:43:02.259', 1, 'Super Admin', 'fa-solid fa-image', 'Gallery', '/office/gallery', 5);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:44:26.023', 1, 'Super Admin', false, '2024-10-15 21:44:26.023', 1, 'Super Admin', 'fa-solid fa-calendar-week', 'Event', '/office/event', 6);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:47:08.878', 1, 'Super Admin', false, '2024-10-15 21:47:08.878', 1, 'Super Admin', 'fa-solid fa-list', 'Menu', '/office/menu', 7);


INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:48:31.386', 1, 'Super Admin', false, '2024-10-15 21:48:31.386', 1, 'Super Admin', 'Data', '/office/user/data', 1, 2);
INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:48:43.492', 1, 'Super Admin', false, '2024-10-15 21:48:43.492', 1, 'Super Admin', 'Role', '/office/user/role', 2, 2);
INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:49:29.207', 1, 'Super Admin', false, '2024-10-15 21:50:05.560', 1, 'Super Admin', 'Data', '/office/committee/data', 1, 3);
INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:51:08.307', 1, 'Super Admin', false, '2024-10-15 21:52:11.962', 1, 'Super Admin', 'Department', '/office/committee/department', 2, 3);


INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:05.948', 1, 1);
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.015', 2, 1);
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.052', 3, 1);
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.075', 4, 1);
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.091', 5, 1);
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.104', 6, 1);
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.121', 7, 1);


INSERT INTO public.t_user_role_sub_menu
(created_at, sub_menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.027', 1, 1);
INSERT INTO public.t_user_role_sub_menu
(created_at, sub_menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.043', 2, 1);
INSERT INTO public.t_user_role_sub_menu
(created_at, sub_menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.061', 3, 1);
INSERT INTO public.t_user_role_sub_menu
(created_at, sub_menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.063', 4, 1);

