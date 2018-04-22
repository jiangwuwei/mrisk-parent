/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/26 17:49:00                           */
/*==============================================================*/


drop table if exists zoom_schedule_job;

drop table if exists zoom_schedule_job_execute_log;

drop table if exists zoom_schedule_job_staff_link;

drop table if exists zoom_schedule_staff;

drop table if exists zoom_schedule_warning_log;

drop table if exists zoom_schedule_warning_template;

/*==============================================================*/
/* Table: zoom_schedule_job                                     */
/*==============================================================*/
create table zoom_schedule_job
(
   id                   bigint(16) not null auto_increment comment '主键',
   job_name             varchar(100) not null comment '作业名',
   job_busi_type        tinyint(4) not null comment '业务系统 1:风控系统  2:授信系统',
   job_mode             tinyint(4) not null comment '作业模式  1: 只有分子型  1:分子分母型 ',
   job_status           tinyint(4) not null comment '作业状态, 1新建 2 调度中  3 停止调度',
   job_desc             varchar(512) comment '作业描述',
   db_selector          varchar(12),
   class_full_path      varchar(512) not null,
   numerator_sql        varchar(2048) comment '作业内容',
   denominator_sql      varchar(2048) comment '作业频率',
   threshold_comparator1 varchar(10) comment ' 阈值比较符',
   threshold1           numeric(16,4),
   threshold_comparator2 varchar(10) comment ' 阈值比较符 ',
   threshold2           numeric(16,4),
   job_frequency        varchar(16) comment 'Json格式的作业频率',
   warning_sms          smallint comment '短信告警: 0 关闭  1开启    默认 0 ',
   warning_email        smallint comment '邮件告警: 0 关闭  1开启    默认 0 ',
   warning_template_id  bigint(16),
   warning_span         int comment '告警频度 单位分钟',
   created_time         datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   modified_time        datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table zoom_schedule_job comment '监控作业';

/*==============================================================*/
/* Table: zoom_schedule_job_execute_log                         */
/*==============================================================*/
create table zoom_schedule_job_execute_log
(
   id                   bigint(16) not null auto_increment comment '主键',
   job_id               varchar(64) not null comment '任务ID',
   job_group            varchar(64) not null comment '任务GROUP',
   taking_time          int comment '任务花费时间',
   job_status           smallint comment '任务结果状态 0 失败 1 成功',
   job_instance_snapshot varchar(4096) comment '任务实例快照',
   job_result_snapshot  varchar(4096) comment '任务结果快照',
   machine              varchar(64),
   start_date           datetime,
   created_date         datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id),
   key key_job_id (job_id)
);

alter table zoom_schedule_job_execute_log comment 'zoom_schedule_job_execute_log';

/*==============================================================*/
/* Table: zoom_schedule_job_staff_link                          */
/*==============================================================*/
create table zoom_schedule_job_staff_link
(
   id                   bigint(16) not null auto_increment comment '主键',
   job_id               bigint(16),
   staff_id             bigint(16) not null comment '联系人外键',
   primary key (id)
);

alter table zoom_schedule_job_staff_link comment '任务联系人连接表';

/*==============================================================*/
/* Table: zoom_schedule_staff                                   */
/*==============================================================*/
create table zoom_schedule_staff
(
   id                   bigint(16) not null auto_increment comment '主键',
   name                 varchar(30) not null comment '联系人名字',
   mobile               varchar(20) comment '手机号码',
   email                varchar(128) not null comment '电子邮件地址',
   description          varchar(255) comment '联系人描述',
   created_time         datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   modified_time        datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table zoom_schedule_staff comment '监控联系人';

/*==============================================================*/
/* Table: zoom_schedule_warning_log                             */
/*==============================================================*/
create table zoom_schedule_warning_log
(
   id                   bigint(16) not null auto_increment comment '主键',
   job_key              varchar(64) not null comment '作业标识',
   job_id               bigint(16) not null comment '作业id',
   subject              varchar(512) not null comment '预警标题',
   content              varchar(2048) not null comment '预警内容',
   created_time         datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   modified_time        datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id),
   key AK_Key_2 (job_key, job_id)
);

alter table zoom_schedule_warning_log comment '监控预警通知记录';

/*==============================================================*/
/* Table: zoom_schedule_warning_template                        */
/*==============================================================*/
create table zoom_schedule_warning_template
(
   id                   bigint(16) not null auto_increment comment '主键',
   job_busi_type        tinyint(4) not null comment '业务类型',
   template_name        varchar(256) not null comment '预警内容',
   template_content     varchar(1024) not null comment '接收人姓名,英文逗号分开',
   created_time         datetime not null default CURRENT_TIMESTAMP comment '创建',
   modified_time        datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table zoom_schedule_warning_template comment '预警记录';

alter table zoom_schedule_job add constraint fk_warning_template_job foreign key (warning_template_id)
      references zoom_schedule_warning_template (id) on delete restrict on update restrict;

alter table zoom_schedule_job_staff_link add constraint FK_Reference_1 foreign key (job_id)
      references zoom_schedule_job (id) on delete restrict on update restrict;

alter table zoom_schedule_job_staff_link add constraint FK_Reference_2 foreign key (staff_id)
      references zoom_schedule_staff (id) on delete restrict on update restrict;

