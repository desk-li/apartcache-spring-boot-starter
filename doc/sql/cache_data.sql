CREATE TABLE `cache_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cacheName` varchar(255) DEFAULT NULL COMMENT '缓存名称',
  `clazz` varchar(255) DEFAULT NULL COMMENT '缓存类',
  `method` varchar(255) DEFAULT NULL COMMENT '缓存方法名',
  `params` text COMMENT '缓存方法参数',
  `status` varchar(20) DEFAULT NULL COMMENT '缓存状态',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人',
  `second` int(11) DEFAULT NULL COMMENT '缓存时间，秒',
  `createTime` datetime DEFAULT NULL,
  `updater` varchar(20) DEFAULT NULL COMMENT '修改人',
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cacheName_unique` (`cacheName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;