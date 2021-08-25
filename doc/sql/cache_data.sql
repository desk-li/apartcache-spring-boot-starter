CREATE TABLE `cache_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cacheName` varchar(255) DEFAULT NULL,
  `clazz` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` text,
  `status` varchar(20) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updater` varchar(20) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;