
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gl_upc_gateway` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `gl_upc_gateway`;

DROP TABLE IF EXISTS `t_gateway_api_define`;
CREATE TABLE `t_gateway_api_define` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `path` varchar(255) NOT NULL COMMENT '路由路径',
  `service_id` varchar(50) DEFAULT NULL COMMENT '服务ID',
  `url` varchar(255) DEFAULT NULL COMMENT '路由根地址',
  `retryable` tinyint(1) DEFAULT NULL COMMENT '是否可重试',
  `enabled` tinyint(1) NOT NULL COMMENT '是否启用',
  `strip_prefix` int(11) DEFAULT NULL COMMENT '去除前缀',
  `api_name` varchar(255) DEFAULT NULL COMMENT 'api名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert  into `t_gateway_api_define`(`id`,`path`,`service_id`,`url`,`retryable`,`enabled`,`strip_prefix`,`api_name`) values
('1','/test/**',NULL,'http://127.0.0.1:8080',1,1,1,'test server');

