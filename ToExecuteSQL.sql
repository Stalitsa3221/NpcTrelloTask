SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `community_skillsave`
-- ----------------------------
CREATE TABLE `community_skillsave` (
  `charId` int(10) NOT NULL,
  `skills` text,
  `pet` text,
  PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of community_skillsave
-- ----------------------------

-- ----------------------------
-- Table structure for `communitybuff`
-- ----------------------------
DROP TABLE IF EXISTS `communitybuff`;
CREATE TABLE `communitybuff` (
  `key` int(11) NOT NULL DEFAULT '0',
  `skillID` int(11) DEFAULT NULL,
  `buff_id` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `itemid` int(11) DEFAULT NULL,
  `skillLvl` int(11) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of communitybuff
-- ----------------------------
