
DROP TABLE IF EXISTS `zoom_bi_roster_100`;
CREATE TABLE `zoom_bi_roster_100` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roster_origin` int(11) NOT NULL COMMENT '名单来源,101:小贷-EUI激活被拒绝用户,102:小贷-EUI使用额度被拒绝用户,103:理财-理财支付被拒绝用户,201:小贷-逾期用户,202:外部-失信,203:外部-多头借贷,204:外部-逾期,301:理财-营销活动被拒绝用户',
  `roster_type` int(11) NOT NULL COMMENT '内容类型',
  `content` varchar(64) NOT NULL COMMENT '名单内容',
  `creator` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delete_flag` tinyint(4) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新日期',
  PRIMARY KEY (`id`),
  KEY `key_roster_content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `zoom_bi_roster_200`;
CREATE TABLE `zoom_bi_roster_200` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roster_origin` int(11) NOT NULL COMMENT '名单来源,101:小贷-EUI激活被拒绝用户,102:小贷-EUI使用额度被拒绝用户,103:理财-理财支付被拒绝用户,201:小贷-逾期用户,202:外部-失信,203:外部-多头借贷,204:外部-逾期,301:理财-营销活动被拒绝用户',
  `roster_type` int(11) NOT NULL COMMENT '内容类型',
  `content` varchar(64) NOT NULL COMMENT '名单内容',
  `creator` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delete_flag` tinyint(4) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新日期',
  PRIMARY KEY (`id`),
  KEY `key_roster_content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `zoom_bi_roster_300`;
CREATE TABLE `zoom_bi_roster_300` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roster_origin` int(11) NOT NULL COMMENT '名单来源,101:小贷-EUI激活被拒绝用户,102:小贷-EUI使用额度被拒绝用户,103:理财-理财支付被拒绝用户,201:小贷-逾期用户,202:外部-失信,203:外部-多头借贷,204:外部-逾期,301:理财-营销活动被拒绝用户',
  `roster_type` int(11) NOT NULL COMMENT '内容类型',
  `content` varchar(64) NOT NULL COMMENT '名单内容',
  `creator` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delete_flag` tinyint(4) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新日期',
  PRIMARY KEY (`id`),
  KEY `key_roster_content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `zoom_bi_roster_log`;
CREATE TABLE `zoom_bi_roster_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roster_busi_type` int(11) NOT NULL COMMENT '名单类型,100:反欺诈，200:信用,300:反欺诈',
  `roster_origin` int(11) NOT NULL COMMENT '名单来源,101:小贷-EUI激活被拒绝用户,102:小贷-EUI使用额度被拒绝用户,103:理财-理财支付被拒绝用户,201:小贷-逾期用户,202:外部-失信,203:外部-多头借贷,204:外部-逾期,301:理财-营销活动被拒绝用户',
  `roster_type` int(11) NOT NULL COMMENT '内容类型 1 uid,  2 手机号码 3 身份证  4 银行卡',
  `content` varchar(64) NOT NULL COMMENT '名单内容',
  `status` smallint(4) NOT NULL DEFAULT '0' COMMENT '状态 1 成功 0 失败',
  `oper_type` smallint(4) DEFAULT NULL COMMENT '1 新增 2 删除',
  `status_desc` varchar(512) DEFAULT NULL COMMENT '错误状态描述',
  `creator` varchar(32) DEFAULT NULL COMMENT '操作人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `key_roster_content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `zoom_bi_roster_restriction`;
CREATE TABLE `zoom_bi_roster_restriction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roster_busi_type` int(11) NOT NULL COMMENT '名单类型,100:反欺诈，200:信用,300:反欺诈',
  `scene_no` varchar(6) NOT NULL COMMENT '场景号',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `roster_unique_key` (`roster_busi_type`,`scene_no`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8;


INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterBusiType', '100', '反欺诈名单');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterBusiType', '200', '信用名单');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterBusiType', '300', '营销名单');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '101', '小贷-EUI激活被拒绝用户');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '102', '小贷-EUI使用额度被拒绝用户');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '103', '理财-理财支付被拒绝用户');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '190', '其他方式');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '201', '小贷-逾期用户');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '202', '外部-失信');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '203', '外部-多头借贷');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '204', '外部-逾期');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '290', '其他方式');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '301', '理财-营销活动被拒绝用户');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterOrigin', '390', '其他方式');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterType', '1', 'uid');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterType', '2', '手机号');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterType', '3', '身份证号码');
INSERT INTO `risk_operating`.`zoom_dim` (`code`, `id`, `name`) VALUES ('RosterType', '4', '银行卡号');