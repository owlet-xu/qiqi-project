/*
 Navicat Premium Data Transfer

 Source Server         : qiqi_sql
 Source Server Type    : SQL Server
 Source Server Version : 12002000
 Source Host           : localhost:1433
 Source Catalog        : qiqi_sql
 Source Schema         : qiqi

 Target Server Type    : SQL Server
 Target Server Version : 12002000
 File Encoding         : 65001

 Date: 19/11/2020 17:17:30
*/


-- ----------------------------
-- Table structure for C_DEPARTMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[C_DEPARTMENT]') AND type IN ('U'))
	DROP TABLE [qiqi].[C_DEPARTMENT]
GO

CREATE TABLE [qiqi].[C_DEPARTMENT] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [NAME] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [PARENT_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [IMG_ID] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [ENABLE] int  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [DEEP_ID] int  NOT NULL
)
GO

ALTER TABLE [qiqi].[C_DEPARTMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for C_MENU
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[C_MENU]') AND type IN ('U'))
	DROP TABLE [qiqi].[C_MENU]
GO

CREATE TABLE [qiqi].[C_MENU] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [NAME] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [URL] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [PARENT_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [ORDER_NUM] bigint  NULL,
  [DEEP_ID] int  NULL,
  [ENABLE] int  NULL,
  [IMG_ID] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [CODE] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [ICON] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [qiqi].[C_MENU] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for C_PRIVILEGE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[C_PRIVILEGE]') AND type IN ('U'))
	DROP TABLE [qiqi].[C_PRIVILEGE]
GO

CREATE TABLE [qiqi].[C_PRIVILEGE] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [NAME] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [ENABLE] int  NULL,
  [CODE] int  NULL
)
GO

ALTER TABLE [qiqi].[C_PRIVILEGE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for C_ROLE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[C_ROLE]') AND type IN ('U'))
	DROP TABLE [qiqi].[C_ROLE]
GO

CREATE TABLE [qiqi].[C_ROLE] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CODE] int  NOT NULL,
  [NAME] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [DEFAULT_DATA] int  NULL,
  [ENABLE] int  NULL
)
GO

ALTER TABLE [qiqi].[C_ROLE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for C_USER
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[C_USER]') AND type IN ('U'))
	DROP TABLE [qiqi].[C_USER]
GO

CREATE TABLE [qiqi].[C_USER] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [NAME] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [USER_NAME] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [PASSWORD] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [MOBILE] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [EMAIL] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [USER_TYPE] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [ENABLE] int  NULL,
  [USER_INTEGRAL] int  NULL,
  [USER_LEVEL] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [HEAD_IMG] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [qiqi].[C_USER] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for R_ROLE_MENU_PRIVILEGE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[R_ROLE_MENU_PRIVILEGE]') AND type IN ('U'))
	DROP TABLE [qiqi].[R_ROLE_MENU_PRIVILEGE]
GO

CREATE TABLE [qiqi].[R_ROLE_MENU_PRIVILEGE] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [ROLE_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [MENU_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [PRIVILEGE_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE] int  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [UPDATE_TIME] datetime2(7)  NULL
)
GO

ALTER TABLE [qiqi].[R_ROLE_MENU_PRIVILEGE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for R_USER_DEPARTMENT_ROLE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[R_USER_DEPARTMENT_ROLE]') AND type IN ('U'))
	DROP TABLE [qiqi].[R_USER_DEPARTMENT_ROLE]
GO

CREATE TABLE [qiqi].[R_USER_DEPARTMENT_ROLE] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [USER_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [DEPT_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [ROLE_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE] int  NULL,
  [CREATE_TIME] datetime2(7)  NULL
)
GO

ALTER TABLE [qiqi].[R_USER_DEPARTMENT_ROLE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Primary Key structure for table C_DEPARTMENT
-- ----------------------------
ALTER TABLE [qiqi].[C_DEPARTMENT] ADD CONSTRAINT [PK_C_DEPARTMENT] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table C_MENU
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [C_MENU_PK]
ON [qiqi].[C_MENU] (
  [ID] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table C_MENU
-- ----------------------------
ALTER TABLE [qiqi].[C_MENU] ADD CONSTRAINT [PK_C_MENU] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table C_PRIVILEGE
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [C_PRIVILEGE_PK]
ON [qiqi].[C_PRIVILEGE] (
  [ID] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table C_PRIVILEGE
-- ----------------------------
ALTER TABLE [qiqi].[C_PRIVILEGE] ADD CONSTRAINT [PK_C_PRIVILEGE] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table C_ROLE
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [C_ROLE_PK]
ON [qiqi].[C_ROLE] (
  [ID] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table C_ROLE
-- ----------------------------
ALTER TABLE [qiqi].[C_ROLE] ADD CONSTRAINT [PK_C_ROLE] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table C_USER
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [C_USER_PK]
ON [qiqi].[C_USER] (
  [ID] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table C_USER
-- ----------------------------
ALTER TABLE [qiqi].[C_USER] ADD CONSTRAINT [PK_C_USER] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table R_ROLE_MENU_PRIVILEGE
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [R_ROLE_MENU_PRIVILEGE_PK]
ON [qiqi].[R_ROLE_MENU_PRIVILEGE] (
  [ID] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table R_ROLE_MENU_PRIVILEGE
-- ----------------------------
ALTER TABLE [qiqi].[R_ROLE_MENU_PRIVILEGE] ADD CONSTRAINT [PK_R_ROLE_MENU_PRIVILEGE] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table R_USER_DEPARTMENT_ROLE
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [R_USER_DEPARTMENT_PK]
ON [qiqi].[R_USER_DEPARTMENT_ROLE] (
  [ID] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table R_USER_DEPARTMENT_ROLE
-- ----------------------------
ALTER TABLE [qiqi].[R_USER_DEPARTMENT_ROLE] ADD CONSTRAINT [PK_R_USER_DEPARTMENT] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

