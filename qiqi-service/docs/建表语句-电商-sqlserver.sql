/*
 Navicat Premium Data Transfer

 Source Server         : qiqi_sql
 Source Server Type    : SQL Server
 Source Server Version : 12002000
 Source Host           : 169.254.59.111:1433
 Source Catalog        : qiqi_sql
 Source Schema         : qiqi

 Target Server Type    : SQL Server
 Target Server Version : 12002000
 File Encoding         : 65001

 Date: 24/02/2021 13:56:38
*/


-- ----------------------------
-- Table structure for B_GOODS
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[B_GOODS]') AND type IN ('U'))
	DROP TABLE [qiqi].[B_GOODS]
GO

CREATE TABLE [qiqi].[B_GOODS] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [NAME] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [PRICE] money  NULL,
  [DISCOUNT] float(53)  NULL,
  [DESCRIPTION] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [DETAIL] nvarchar(2000) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE1] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE2] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [PIC1] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [PIC2] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [ENABLE] int  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [CREATE_TIME] datetime2(7)  NULL
)
GO

ALTER TABLE [qiqi].[B_GOODS] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for B_ORDERS
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[B_ORDERS]') AND type IN ('U'))
	DROP TABLE [qiqi].[B_ORDERS]
GO

CREATE TABLE [qiqi].[B_ORDERS] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [ORDER_NUM] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [ORDER_TYPE] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [ENABLE] int  NULL,
  [PRICE] money  NULL,
  [CUSTOMER_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [PHONE] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [ADDRESS] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [COURIER_NUM] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [CREATE_TIME] datetime2(7)  NULL
)
GO

ALTER TABLE [qiqi].[B_ORDERS] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Primary Key structure for table B_GOODS
-- ----------------------------
ALTER TABLE [qiqi].[B_GOODS] ADD CONSTRAINT [PK__B_GOODS__3214EC279042BD2C] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table B_ORDERS
-- ----------------------------
ALTER TABLE [qiqi].[B_ORDERS] ADD CONSTRAINT [PK__B_ORDERS__3214EC271447C70F] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

