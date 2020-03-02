/*==============================================================*/
/* Table: C_DEPARTMENT                                          */
/*==============================================================*/
create table qiqi.C_DEPARTMENT 
(
   ID                   varchar(50)                    not null,
   NAME                 varchar(200)                   null,
   PARENT_ID            varchar(50)                    null,
   CREATE_TIME          datetime2                      null,
   constraint PK_C_DEPARTMENT primary key (ID)
);

/*==============================================================*/
/* Table: C_MENU                                                */
/*==============================================================*/
create table qiqi.C_MENU 
(
   ID                   varchar(50)                    not null,
   NAME                 varchar(200)                   null,
   URL                  varchar(200)                   null,
   CREATE_TIME          datetime2                      null,
   constraint PK_C_MENU primary key (ID)
);

/*==============================================================*/
/* Index: C_MENU_PK                                             */
/*==============================================================*/
create unique index C_MENU_PK on qiqi.C_MENU (
ID ASC
);

/*==============================================================*/
/* Table: C_PRIVILEGE                                           */
/*==============================================================*/
create table qiqi.C_PRIVILEGE 
(
   ID                   varchar(50)                    not null,
   NAME                 varchar(200)                   null,
   CREATE_TIME          varchar(255)                   null,
   constraint PK_C_PRIVILEGE primary key (ID)
);

/*==============================================================*/
/* Index: C_PRIVILEGE_PK                                        */
/*==============================================================*/
create unique index C_PRIVILEGE_PK on qiqi.C_PRIVILEGE (
ID ASC
);

/*==============================================================*/
/* Table: C_ROLE                                                */
/*==============================================================*/
create table qiqi.C_ROLE 
(
   ID                   varchar(50)                    not null,
   CODE                 integer                        null,
   NAME                 varchar(200)                   null,
   CREATE_TIME          varchar(255)                   null,
   constraint PK_C_ROLE primary key (ID)
);

/*==============================================================*/
/* Index: C_ROLE_PK                                             */
/*==============================================================*/
create unique index C_ROLE_PK on qiqi.C_ROLE (
ID ASC
);

/*==============================================================*/
/* Table: C_USER                                                */
/*==============================================================*/
create table qiqi.C_USER 
(
   ID                   varchar(50)                    not null,
   NAME                 varchar(100)                   null,
   USER_NAME            varchar(100)                   null,
   PASSWORD             varchar(50)                    null,
   MOBILE               varchar(20)                    null,
   EMAIL                varchar(100)                   null,
   USER_TYPE            varchar(20)                    null,
   ENABLE               integer                        null,
   USER_INTEGRAL        integer                        null,
   USER_LEVEL           varchar(100)                   null,
   UPDATE_TIME          datetime2                      null,
   CREATE_TIME          datetime2                      null,
   constraint PK_C_USER primary key (ID)
);

/*==============================================================*/
/* Index: C_USER_PK                                             */
/*==============================================================*/
create unique index C_USER_PK on qiqi.C_USER (
ID ASC
);

/*==============================================================*/
/* Table: R_ROLE_MENU_PRIVILEGE                                 */
/*==============================================================*/
create table qiqi.R_ROLE_MENU_PRIVILEGE 
(
   ID                   varchar(50)                    not null,
   ROLE_ID              varchar(50)                    null,
   MENU_ID              varchar(50)                    null,
   PRIVILEGE_ID         varchar(50)                    null,
   TYPE                 integer                        null,
   CREATE_TIME          datetime2                      null,
   constraint PK_R_ROLE_MENU_PRIVILEGE primary key (ID)
);

/*==============================================================*/
/* Index: R_ROLE_MENU_PRIVILEGE_PK                              */
/*==============================================================*/
create unique index R_ROLE_MENU_PRIVILEGE_PK on qiqi.R_ROLE_MENU_PRIVILEGE (
ID ASC
);

/*==============================================================*/
/* Table: R_USER_DEPARTMENT                                     */
/*==============================================================*/
create table qiqi.R_USER_DEPARTMENT 
(
   ID                   varchar(50)                    not null,
   USER_ID              varchar(50)                    null,
   DEPT_ID              varchar(50)                    null,
   ROLE_ID              varchar(50)                    null,
   TYPE                 integer                        null,
   CREATE_TIME          datetime2                      null,
   constraint PK_R_USER_DEPARTMENT primary key (ID)
);

/*==============================================================*/
/* Index: R_USER_DEPARTMENT_PK                                  */
/*==============================================================*/
create unique index R_USER_DEPARTMENT_PK on qiqi.R_USER_DEPARTMENT (
ID ASC
);

