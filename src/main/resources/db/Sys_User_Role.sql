CREATE TABLE `Sys_User_Role` (
  `User_Code` varchar(30) NOT NULL COMMENT '人员编码',
  `Role_Code` varchar(30) NOT NULL COMMENT '角色编码',
  `Corp_Code` varchar(30) NOT NULL COMMENT '公司编号',
  `Mem` varchar(2000) DEFAULT NULL COMMENT '备注',
  `Ur_Create` varchar(30) DEFAULT NULL COMMENT '创建者',
  `Date_Create` datetime DEFAULT NULL COMMENT '创建时间',
  `Ur_Alter` varchar(30) DEFAULT NULL COMMENT '修改者',
  `Date_Alter` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`User_Code`,`Corp_Code`,`Role_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Bas_User_Role';
