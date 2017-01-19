-- ----------------------------
-- Table structure for tb_content
-- ----------------------------
DROP TABLE IF EXISTS `tb_content`;
CREATE TABLE `tb_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL COMMENT '������ĿID',
  `title` varchar(200) DEFAULT NULL COMMENT '���ݱ���',
  `sub_title` varchar(100) DEFAULT NULL COMMENT '�ӱ���',
  `title_desc` varchar(500) DEFAULT NULL COMMENT '��������',
  `url` varchar(500) DEFAULT NULL COMMENT '����',
  `pic` varchar(300) DEFAULT NULL COMMENT 'ͼƬ����·��',
  `pic2` varchar(300) DEFAULT NULL COMMENT 'ͼƬ2',
  `content` text COMMENT '����',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `updated` (`updated`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_content_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_content_category`;
CREATE TABLE `tb_content_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '��ĿID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '����ĿID=0ʱ���������һ������Ŀ',
  `name` varchar(50) DEFAULT NULL COMMENT '��������',
  `status` int(1) DEFAULT '1' COMMENT '״̬����ѡֵ:1(����),2(ɾ��)',
  `sort_order` int(4) DEFAULT NULL COMMENT '������ţ���ʾͬ����Ŀ��չ�ִ�������ֵ��������ƴ������С�ȡֵ��Χ:�����������',
  `is_parent` tinyint(1) DEFAULT '1' COMMENT '����Ŀ�Ƿ�Ϊ����Ŀ��1Ϊtrue��0Ϊfalse',
  `created` datetime DEFAULT NULL COMMENT '����ʱ��',
  `updated` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`,`status`) USING BTREE,
  KEY `sort_order` (`sort_order`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='���ݷ���';

-- ----------------------------
-- Table structure for tb_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_item`;
CREATE TABLE `tb_item` (
  `id` bigint(20) NOT NULL COMMENT '��Ʒid��ͬʱҲ����Ʒ���',
  `title` varchar(100) NOT NULL COMMENT '��Ʒ����',
  `sell_point` varchar(500) DEFAULT NULL COMMENT '��Ʒ����',
  `price` bigint(20) NOT NULL COMMENT '��Ʒ�۸񣬵�λΪ����',
  `num` int(10) NOT NULL COMMENT '�������',
  `barcode` varchar(30) DEFAULT NULL COMMENT '��Ʒ������',
  `image` varchar(500) DEFAULT NULL COMMENT '��ƷͼƬ',
  `cid` bigint(10) NOT NULL COMMENT '������Ŀ��Ҷ����Ŀ',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '��Ʒ״̬��1-������2-�¼ܣ�3-ɾ��',
  `created` datetime NOT NULL COMMENT '����ʱ��',
  `updated` datetime NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`),
  KEY `status` (`status`),
  KEY `updated` (`updated`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ʒ��';

-- ----------------------------
-- Table structure for tb_item_cat
-- ----------------------------
DROP TABLE IF EXISTS `tb_item_cat`;
CREATE TABLE `tb_item_cat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '��ĿID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '����ĿID=0ʱ���������һ������Ŀ',
  `name` varchar(50) DEFAULT NULL COMMENT '��Ŀ����',
  `status` int(1) DEFAULT '1' COMMENT '״̬����ѡֵ:1(����),2(ɾ��)',
  `sort_order` int(4) DEFAULT NULL COMMENT '������ţ���ʾͬ����Ŀ��չ�ִ�������ֵ��������ƴ������С�ȡֵ��Χ:�����������',
  `is_parent` tinyint(1) DEFAULT '1' COMMENT '����Ŀ�Ƿ�Ϊ����Ŀ��1Ϊtrue��0Ϊfalse',
  `created` datetime DEFAULT NULL COMMENT '����ʱ��',
  `updated` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`,`status`) USING BTREE,
  KEY `sort_order` (`sort_order`)
) ENGINE=InnoDB AUTO_INCREMENT=1183 DEFAULT CHARSET=utf8 COMMENT='��Ʒ��Ŀ';

-- ----------------------------
-- Table structure for tb_item_desc
-- ----------------------------
DROP TABLE IF EXISTS `tb_item_desc`;
CREATE TABLE `tb_item_desc` (
  `item_id` bigint(20) NOT NULL COMMENT '��ƷID',
  `item_desc` text COMMENT '��Ʒ����',
  `created` datetime DEFAULT NULL COMMENT '����ʱ��',
  `updated` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ʒ������';

-- ----------------------------
-- Table structure for tb_item_param
-- ----------------------------
DROP TABLE IF EXISTS `tb_item_param`;
CREATE TABLE `tb_item_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_cat_id` bigint(20) DEFAULT NULL COMMENT '��Ʒ��ĿID',
  `param_data` text COMMENT '�������ݣ���ʽΪjson��ʽ',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_cat_id` (`item_cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='��Ʒ�������';

-- ----------------------------
-- Table structure for tb_item_param_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_item_param_item`;
CREATE TABLE `tb_item_param_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) DEFAULT NULL COMMENT '��ƷID',
  `param_data` text COMMENT '�������ݣ���ʽΪjson��ʽ',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='��Ʒ������Ʒ�Ĺ�ϵ��';

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `order_id` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '����id',
  `payment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'ʵ������ȷ��2λС��;��λ:Ԫ����:200.07����ʾ:200Ԫ7��',
  `payment_type` int(2) DEFAULT NULL COMMENT '֧�����ͣ�1������֧����2����������',
  `post_fee` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '�ʷѡ���ȷ��2λС��;��λ:Ԫ����:200.07����ʾ:200Ԫ7��',
  `status` int(10) DEFAULT NULL COMMENT '״̬��1��δ���2���Ѹ��3��δ������4���ѷ�����5�����׳ɹ���6�����׹ر�',
  `create_time` datetime DEFAULT NULL COMMENT '��������ʱ��',
  `update_time` datetime DEFAULT NULL COMMENT '��������ʱ��',
  `payment_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `consign_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `end_time` datetime DEFAULT NULL COMMENT '�������ʱ��',
  `close_time` datetime DEFAULT NULL COMMENT '���׹ر�ʱ��',
  `shipping_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '��������',
  `shipping_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '��������',
  `user_id` bigint(20) DEFAULT NULL COMMENT '�û�id',
  `buyer_message` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '�������',
  `buyer_nick` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '����ǳ�',
  `buyer_rate` int(2) DEFAULT NULL COMMENT '����Ƿ��Ѿ�����',
  PRIMARY KEY (`order_id`),
  KEY `create_time` (`create_time`),
  KEY `buyer_nick` (`buyer_nick`),
  KEY `status` (`status`),
  KEY `payment_type` (`payment_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for tb_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL,
  `item_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '��Ʒid',
  `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '����id',
  `num` int(10) DEFAULT NULL COMMENT '��Ʒ��������',
  `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '��Ʒ����',
  `price` bigint(50) DEFAULT NULL COMMENT '��Ʒ����',
  `total_fee` bigint(50) DEFAULT NULL COMMENT '��Ʒ�ܽ��',
  `pic_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '��ƷͼƬ��ַ',
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tb_order_shipping
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_shipping`;
CREATE TABLE `tb_order_shipping` (
  `order_id` varchar(50) NOT NULL COMMENT '����ID',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '�ջ���ȫ��',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '�̶��绰',
  `receiver_mobile` varchar(30) DEFAULT NULL COMMENT '�ƶ��绰',
  `receiver_state` varchar(10) DEFAULT NULL COMMENT 'ʡ��',
  `receiver_city` varchar(10) DEFAULT NULL COMMENT '����',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '��/��',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '�ջ���ַ���磺xx·xx��',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '��������,�磺310001',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '�û���',
  `password` varchar(32) NOT NULL COMMENT '���룬���ܴ洢',
  `phone` varchar(20) DEFAULT NULL COMMENT 'ע���ֻ���',
  `email` varchar(50) DEFAULT NULL COMMENT 'ע������',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='�û���';

