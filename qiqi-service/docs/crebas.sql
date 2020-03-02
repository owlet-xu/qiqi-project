/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     2020/3/2 16:20:59                            */
/*==============================================================*/


drop table if exists C_APPRAISE;

drop table if exists C_DEPARTMENT;

drop index if exists C_GOODS.C_GOODS_PK;

drop table if exists C_GOODS;

drop index if exists C_MENU.C_MENU_PK;

drop table if exists C_MENU;

drop table if exists C_ORDERS;

drop index if exists C_PRIVILEGE.C_PRIVILEGE_PK;

drop table if exists C_PRIVILEGE;

drop index if exists C_ROLE.C_ROLE_PK;

drop table if exists C_ROLE;

drop index if exists C_USER.C_USER_PK;

drop table if exists C_USER;

drop table if exists R_ORDER_GOODS;

drop index if exists R_ROLE_MENU_PRIVILEGE.R_ROLE_MENU_PRIVILEGE_PK;

drop table if exists R_ROLE_MENU_PRIVILEGE;

drop index if exists R_USER_DEPARTMENT.R_USER_DEPARTMENT_PK;

drop table if exists R_USER_DEPARTMENT;

/*==============================================================*/
/* Table: C_APPRAISE                                            */
/*==============================================================*/
create table C_APPRAISE 
(
   ID                   char(10)                       null,
   GOODS_ID             char(10)                       null,
   CONTENT              char(10)                       null,
   CREATE_TIME          char(10)                       null
);

/*==============================================================*/
/* Table: C_DEPARTMENT                                          */
/*==============================================================*/
create table C_DEPARTMENT 
(
   ID                   varchar(50)                    null,
   NAME                 varchar(200)                   null,
   PARENT_ID            varchar(50)                    null,
   CREATE_TIME          timestamp                      null
);

/*==============================================================*/
/* Table: C_GOODS                                               */
/*==============================================================*/
create table C_GOODS 
(
   ID                   char(10)                       not null,
   ��Ʒ����                 char(10)                       null,
   ��Ʒ�۸�                 char(10)                       null,
   ��Ʒ�ۿ�                 char(10)                       null,
   ����������               char(10)                       null,
   ��Ʒ����1                char(10)                       null,
   ��Ʒ����2                char(10)                       null,
   ��ƷͼƬ                 char(10)                       null,
   ���ý���                 char(10)                       null,
   ����ʱ��                 char(10)                       null,
   constraint PK_C_GOODS primary key (ID)
);

/*==============================================================*/
/* Index: C_GOODS_PK                                            */
/*==============================================================*/
create unique index C_GOODS_PK on C_GOODS (
ID ASC
);

/*==============================================================*/
/* Table: C_MENU                                                */
/*==============================================================*/
create table C_MENU 
(
   ID                   varchar(50)                    not null,
   NAME                 varchar(200)                   null,
   URL                  varchar(200)                   null,
   CREATE_TIME          timestamp                      null,
   constraint PK_C_MENU primary key (ID)
);

/*==============================================================*/
/* Index: C_MENU_PK                                             */
/*==============================================================*/
create unique index C_MENU_PK on C_MENU (
ID ASC
);

/*==============================================================*/
/* Table: C_ORDERS                                              */
/*==============================================================*/
create table C_ORDERS 
(
   ID                   char(10)                       null,
   ������                  char(10)                       null,
   ��Ʒ����ID               char(10)                       null,
   �Ƿ�ʧЧ                 char(10)                       null,
   �ܽ��                  char(10)                       null,
   �޸�ʱ��                 char(10)                       null,
   ����ʱ��                 char(10)                       null
);

/*==============================================================*/
/* Table: C_PRIVILEGE                                           */
/*==============================================================*/
create table C_PRIVILEGE 
(
   ID                   varchar(50)                    not null,
   NAME                 varchar(200)                   null,
   CREATE_TIME          varchar(255)                   null,
   constraint PK_C_PRIVILEGE primary key (ID)
);

/*==============================================================*/
/* Index: C_PRIVILEGE_PK                                        */
/*==============================================================*/
create unique index C_PRIVILEGE_PK on C_PRIVILEGE (
ID ASC
);

/*==============================================================*/
/* Table: C_ROLE                                                */
/*==============================================================*/
create table C_ROLE 
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
create unique index C_ROLE_PK on C_ROLE (
ID ASC
);

/*==============================================================*/
/* Table: C_USER                                                */
/*==============================================================*/
create table C_USER 
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
   UPDATE_TIME          timestamp                      null,
   CREATE_TIME          timestamp                      null,
   constraint PK_C_USER primary key (ID)
);

comment on column C_USER.USER_LEVEL is 
'1����ͨ��Ա  2���ƽ��Ա 3����ʯ��Ա';

/*==============================================================*/
/* Index: C_USER_PK                                             */
/*==============================================================*/
create unique index C_USER_PK on C_USER (
ID ASC
);

/*==============================================================*/
/* Table: R_ORDER_GOODS                                         */
/*==============================================================*/
create table R_ORDER_GOODS 
(
   ID                   char(10)                       null,
   ORDER_ID             char(10)                       null,
   GOODS_ID             char(10)                       null,
   CREATE_TIME          char(10)                       null
);

/*==============================================================*/
/* Table: R_ROLE_MENU_PRIVILEGE                                 */
/*==============================================================*/
create table R_ROLE_MENU_PRIVILEGE 
(
   ID                   varchar(50)                    not null,
   ROLE_ID              varchar(50)                    null,
   MENU_ID              varchar(50)                    null,
   PRIVILEGE_ID         varchar(50)                    null,
   TYPE                 integer                        null,
   CREATE_TIME          timestamp                      null,
   constraint PK_R_ROLE_MENU_PRIVILEGE primary key (ID)
);

/*==============================================================*/
/* Index: R_ROLE_MENU_PRIVILEGE_PK                              */
/*==============================================================*/
create unique index R_ROLE_MENU_PRIVILEGE_PK on R_ROLE_MENU_PRIVILEGE (
ID ASC
);

/*==============================================================*/
/* Table: R_USER_DEPARTMENT                                     */
/*==============================================================*/
create table R_USER_DEPARTMENT 
(
   ID                   varchar(50)                    not null,
   USER_ID              varchar(50)                    null,
   DEPT_ID              varchar(50)                    null,
   ROLE_ID              varchar(50)                    null,
   TYPE                 integer                        null,
   CREATE_TIME          timestamp                      null,
   constraint PK_R_USER_DEPARTMENT primary key (ID)
);

/*==============================================================*/
/* Index: R_USER_DEPARTMENT_PK                                  */
/*==============================================================*/
create unique index R_USER_DEPARTMENT_PK on R_USER_DEPARTMENT (
ID ASC
);

