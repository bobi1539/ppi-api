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
VALUES('2024-10-15 21:32:24.400', 1, 'Super Admin', false, '2024-10-15 21:32:24.400', 1, 'Super Admin', 'User Super admin', 'superadmin@gmail.com', '2024-10-15 21:32:24.400', true, 'Super Admin', '$2a$12$U7bA2JY5ni/nveWj6sc53OC5HXNONc4XEYVPZDM3k7PQo8cH/Q5oK', NULL, 'superadmin', 1);


INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:38:45.262', 1, 'Super Admin', false, '2024-10-15 21:38:45.262', 1, 'Super Admin', 'fa-solid fa-gauge-high', 'Dashboard', '/office/dashboard', 1);
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:39:57.034', 1, 'Super Admin', false, '2024-10-15 21:39:57.034', 1, 'Super Admin', 'fa-solid fa-user-gear', 'User', '/office/user', 2);
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
INSERT INTO public.m_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, icon, "name", route, "sequence")
VALUES('2024-10-15 21:47:08.878', 1, 'Super Admin', false, '2024-10-15 21:47:08.878', 1, 'Super Admin', 'fa-solid fa-gear', 'Setting', '/office/setting', 8);


INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:48:31.386', 1, 'Super Admin', false, '2024-10-15 21:48:31.386', 1, 'Super Admin', 'Data', '/office/user/data', 1, 2);
INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:48:43.492', 1, 'Super Admin', false, '2024-10-15 21:48:43.492', 1, 'Super Admin', 'Role', '/office/user/role', 2, 2);
INSERT INTO public.m_sub_menu
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, "name", route, "sequence", menu_id)
VALUES('2024-10-15 21:48:43.492', 1, 'Super Admin', false, '2024-10-15 21:48:43.492', 1, 'Super Admin', 'Student', '/office/user/student', 3, 2);
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
INSERT INTO public.t_user_role_menu
(created_at, menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.121', 8, 1);


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
INSERT INTO public.t_user_role_sub_menu
(created_at, sub_menu_id, user_role_id)
VALUES('2024-10-15 21:58:06.063', 5, 1);

INSERT INTO public.m_setting
(created_at, created_by, created_by_name, is_deleted, updated_at, updated_by, updated_by_name, banner, contact_email, contact_phone_number, instagram, linkedin, logo, qr_code, support_account_name, support_account_number, support_short_code, tiktok, youtube, event_id, period_id)
VALUES('2024-11-03 20:53:01.886', 1, 'Super Admin', false, '2024-11-05 11:05:15.518', 1, 'Super Admin', 'PPI_Warwick_IelUKSV56R6h6SSnoSU7_1730779515467_banner.png', 'ppiwarwick@gmail.com', 'Angel : +447585423161', 'https://www.instagram.com/ppiwarwick/', 'https://www.linkedin.com/company/ppi-warwick/', 'PPI_Warwick_iwB1Rm9mhhQIHsjvyZ1t_1730773376533_logo.png', 'PPI_Warwick_f6nEOtxPdf5bYfGzocie_1730773376570_qr.png', 'DINA MAULIA', '93777928', '20-26-22', 'https://www.tiktok.com/@ppiwarwick', 'https://www.youtube.com/@ppiwarwick2502', NULL, NUll);

INSERT INTO public.t_secret_key
(created_at, "key", "name", valid_date)
VALUES('2024-11-06 09:07:54.046', 'RjxCSsz4nFts54MvC8gv', 'student-form-key', '2024-11-18 17:06:20.803');

