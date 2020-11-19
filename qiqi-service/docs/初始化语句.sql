---超级管理员用户
INSERT INTO [qiqi_sql].[qiqi].[C_USER] ([ID], [NAME], [USER_NAME], [PASSWORD], [MOBILE], [EMAIL], [USER_TYPE], [ENABLE], [USER_INTEGRAL], [USER_LEVEL], [UPDATE_TIME], [CREATE_TIME], [HEAD_IMG]) VALUES ('60de0df0-5d01-4189-9161-a633df090f77', N'admin', N'admin', '80a0580d894583d17e880506abe93eb9', '17775338917', '', '1', '1', '0', '', '2020-03-25 16:34:31.2920000', '2020-03-13 15:44:49.0000000', '1584702171509.png');
---超级管理员角色
INSERT INTO [qiqi_sql].[qiqi].[C_ROLE] ([ID], [CODE], [NAME], [CREATE_TIME], [UPDATE_TIME], [DEFAULT_DATA], [ENABLE]) VALUES ('e1dc85b2-f366-45ae-9499-1e6d515983ae', '-1', N'admin', '2020-03-31 11:16:47.7350000', '2020-03-31 11:16:47.7350000', '1', '1');
---给超级管理员用户加角色
INSERT INTO [qiqi_sql].[qiqi].[R_USER_DEPARTMENT_ROLE] ([ID], [USER_ID], [DEPT_ID], [ROLE_ID], [TYPE], [CREATE_TIME]) VALUES ('34b06dec-3196-4d84-8f64-0b7e0bc8a850', '60de0df0-5d01-4189-9161-a633df090f77', NULL, 'e1dc85b2-f366-45ae-9499-1e6d515983ae', '1', '2020-04-02 15:55:16.8480000');




---菜单初始化
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('0633a0b9-62a1-42cc-8a5b-8a8c29843c3e', N'首页', '/home', '2020-03-31 16:28:09.0000000', '2020-04-07 14:35:01.2540000', '', 0, 0, 1, '', N'Home', N'dashboard');
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('1b43140d-26a3-40ad-8ead-dfdbede24ed4', N'权限列表', '/privilege/list', '2020-04-02 16:32:01.7380000', '2020-04-02 16:32:01.7380000', 'bbad1c9b-9a0f-44ec-81aa-75af21dabd25', 8, 0, 1, '', N'PrivilegeList', N'icon');
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('50a5bd8f-1e56-4ce0-a743-068f4c6aad26', N'部门管理', '/dept-manage', '2020-04-02 16:26:18.0000000', '2020-04-02 16:28:53.7390000', '', 7, 0, 1, '', N'DeptManage', N'icon');
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('986ac917-0bdd-4bd9-9485-6324871ff267', N'菜单管理', '/privilege/menu-manage', '2020-04-02 16:31:34.0670000', '2020-04-02 16:31:34.0670000', 'bbad1c9b-9a0f-44ec-81aa-75af21dabd25', 5, 0, 1, '', N'MenuManage', N'icon');
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('a43761ba-6ff5-42bb-a899-7cd674e66b21', N'用户管理', '/user-manage', '2020-03-31 16:34:29.0000000', '2020-04-02 16:25:59.9470000', '', 6, 0, 1, '', N'UserManage', N'icon');
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('bbad1c9b-9a0f-44ec-81aa-75af21dabd25', N'权限管理', '/privilege', '2020-03-31 16:29:38.0000000', '2020-04-02 16:30:14.1330000', '', 10, 0, 1, '', N'Privilege', N'icon');
INSERT INTO [qiqi].[C_MENU]([ID], [NAME], [URL], [CREATE_TIME], [UPDATE_TIME], [PARENT_ID], [ORDER_NUM], [DEEP_ID], [ENABLE], [IMG_ID], [CODE], [ICON]) VALUES ('d7b48ddb-6162-4287-b888-c32c8b36ea76', N'角色管理', '/privilege/role-manage', '2020-04-01 14:23:14.0000000', '2020-04-02 16:30:55.6820000', 'bbad1c9b-9a0f-44ec-81aa-75af21dabd25', 1, 0, 1, '', N'RoleManage', N'icon');
