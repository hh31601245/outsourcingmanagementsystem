/*
Navicat MySQL Data Transfer

Source Server         : HH
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : outsourcingmanagementsystem

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-07-13 12:16:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `project_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `project_expected_endtime` datetime(6) DEFAULT NULL,
  `project_requirement_document` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `project_status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `project_employer_id` bigint(20) DEFAULT NULL,
  `project_contractor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('5', '123', '123', '2019-07-24 00:00:00.000000', '新建 Microsoft Word 文档 (6).docx', '已验收', '12', '13');
INSERT INTO `project` VALUES ('6', '1234', '1234', '2019-07-26 00:00:00.000000', '新建 Microsoft Word 文档 (6).docx', '已验收', '12', '13');
INSERT INTO `project` VALUES ('7', '22', '22', '2019-07-31 00:00:00.000000', '新建 Microsoft Word 文档 (6).docx', '已发布', '12', null);
INSERT INTO `project` VALUES ('8', '233', '233', '2019-07-19 00:00:00.000000', 'data.txt', '已发布', '12', null);

-- ----------------------------
-- Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `task_design_document` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `task_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `task_expected_person_days` bigint(20) DEFAULT NULL,
  `task_start_time` datetime(6) DEFAULT NULL,
  `task_end_time` datetime(6) DEFAULT NULL,
  `task_actual_person_days` bigint(20) DEFAULT NULL,
  `task_status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `task_developers_id` bigint(20) DEFAULT NULL,
  `task_project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('7', '123', '', '123', '123', '2019-07-14 00:00:00.000000', '2019-07-18 00:00:00.000000', '4', '已完成', '14', '5');
INSERT INTO `task` VALUES ('8', '456', '', '456', '123', '2019-07-15 00:00:00.000000', '2019-07-25 00:00:00.000000', '9', '已完成', '14', '5');
INSERT INTO `task` VALUES ('9', '159', 'test.docx', '159', '12', '2019-07-15 00:00:00.000000', '2019-07-30 00:00:00.000000', '12', '已完成', '14', '6');
INSERT INTO `task` VALUES ('10', '145', '', '541', '15', '2019-07-17 00:00:00.000000', '2019-07-25 00:00:00.000000', '7', '已完成', '15', '6');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_role` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_leader_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', 'ab3fd836dca7e5ad1920bf6761e7c8e619e437757797f218ce9860ae544e5622d95941a278c850e1dcb1ffc5543860994eb092ae99dbffd79db5b6393edd4bff', '发包方', '0baddf9d69940e6a', null);
INSERT INTO `user` VALUES ('2', '22', '64e675dc9d56ebe3fd74c9ab83af60d962ae02cb225a3842bb958a72c35662e35d267d0dd8e9049c650a12fe4451133578e69adaba2a57ef8fbc0ead5e0cd17c', '承包方管理员', 'c5b54a17c209beae', null);
INSERT INTO `user` VALUES ('4', '3', 'e9f67a4592cb950cbf270b261389c4f976d6d239e836fcd7c61f7901910bbc8fe82b2bc3241942d7b76fde17a06ff64c8adea031ca538d153c460ad72612d07a', '开发人员', 'cc4966007917c41b', '2');
INSERT INTO `user` VALUES ('5', '11', 'ac8830f43957d2dbdded0e1fd4dae9c45c15b7097c2447d0139a2f22b02386657d76b576abb8e5d11f577747d41f28ec158427a19b8b707f2700771b87756a20', '发包方', 'ce4c7e1e6c261ae4', null);
INSERT INTO `user` VALUES ('6', '12', '21d78fd82234408695c3b7f8baae9a85c7db26409a4c768b25347040ea58f3bc6d8430cb5c781e04822f731f6f42d9afa9c86f8c3301816793d16eaaf640acca', '承包方管理员', '134205022d47244d', null);
INSERT INTO `user` VALUES ('7', '123', '3433cf91b5a76919d0cb01534e562646bef5fd17b07296a9b4fff81863dabef11dba1f491e4302b9e27731d2545c793612ff3e2d599a58ca3317cd5cac5b29c1', '承包方管理员', 'ae92d957b4108594', null);
INSERT INTO `user` VALUES ('8', '456', '481748a0bec18a893f650deba3600474486c1c90722e1d9dac0b70ee72cf850bfadec122606062036ee8f06e1b5090b9d085d739771bb85191bea0ece7767683', '承包方管理员', 'f78788c46c221c99', null);
INSERT INTO `user` VALUES ('9', '321', '9a1aa8de00440f9b75a12866c382dbe18fd5ce38e16c929db17aea47fe0821f84d60b09c980d384f9733c54d99d1e3fed9ec4efb54977821873ab471f7a5dfe2', '承包方管理员', '5ab6e687a3afe483', null);
INSERT INTO `user` VALUES ('10', '145', '2f2f02183411a8635b3b3327c1b8837f697c4169294f57f494e189dcb4be38b4f5ea01b99e4c40a6c1a85dd17aee801432e7f8fa7b3eb9a7c46c9d910b09b0cc', '开发人员', '9fc6c4d403a3fb2a', '2');
INSERT INTO `user` VALUES ('11', '154', '34ed39bdb19f3ff57a5c8eadca30584b8751f3461ab819a5af6e308927328feaf601a7070bd3aef98e53adf148a49ff9b0366fb317842049084183e0649e4903', '开发人员', '6d250c55d869e309', '2');
INSERT INTO `user` VALUES ('12', '741', '249c203ae7386f04870f164bbeffe9f6993df0a45790687fa8d730c8b148ae301d31728ef5d4df35d0c3839cc4eb53c26648ae7bf1e6b898b26c8696997c159a', '发包方', '3191a19d45ecf853', null);
INSERT INTO `user` VALUES ('13', '147', '4107bc6113250cfea955ef67d6a9e34c29b9f1c43283303ef831bad307119c8f4dbd111695af35f0c4df1916ad51db98b046d7335c268c1ec6cc1ab2c652d677', '承包方管理员', '504ffa1928956b4a', null);
INSERT INTO `user` VALUES ('14', '417', 'd738a8b2b5baeec24afe7769c42a8430fd90e3c617420a15598f062d0bb27f7ac6f370eaaca964b4c7de860102b745fad7ce5c4b4b117b50eb59866a0c962279', '开发人员', 'bb93ecf34a3d1d8e', '13');
INSERT INTO `user` VALUES ('15', '159', '21b57274abc980c150ca63f67d2b91b6dff4ecf229c0bbdc70b34ed8c23395ecfd42bcfe6d8293ba36d374872787026a3af5e27077d7ba501c7785af79bf7941', '开发人员', 'a6dad26ee848598e', '13');
