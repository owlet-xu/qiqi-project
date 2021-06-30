IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[B_GOODS]') AND type IN ('U'))
	DROP TABLE [qiqi].[B_GOODS]
GO

CREATE TABLE [qiqi].[B_GOODS] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [NAME] nvarchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
  [PRICE] money  NULL,
  [DISCOUNT] float(53)  NULL,
  [DESCRIPTION] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE1] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [TYPE2] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [FILE1] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [FILE2] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [ENABLE] int  NULL,
  [UPDATE_TIME] datetime2(7)  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [FILE3] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [FILE4] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [FILE5] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [FILE6] nvarchar(500) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [qiqi].[B_GOODS] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for B_GOODS_DETAIL
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[qiqi].[B_GOODS_DETAIL]') AND type IN ('U'))
	DROP TABLE [qiqi].[B_GOODS_DETAIL]
GO

CREATE TABLE [qiqi].[B_GOODS_DETAIL] (
  [ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [GOODS_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [DETAIL] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [CREATE_TIME] datetime2(7)  NULL,
  [DETAIL_BG] varchar(500) COLLATE Chinese_PRC_CI_AS  NULL,
  [UPDATE_TIME] datetime2(7)  NULL
)
GO

ALTER TABLE [qiqi].[B_GOODS_DETAIL] SET (LOCK_ESCALATION = TABLE)
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
