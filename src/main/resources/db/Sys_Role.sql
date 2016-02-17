CREATE TABLE `Sys_Role` (
  `Role_Code` varchar(30) NOT NULL COMMENT '角色编码',
  `Role_Name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `Mem` varchar(2000) DEFAULT NULL COMMENT '备注',
  `Ur_Create` varchar(30) DEFAULT NULL COMMENT '创建者',
  `Date_Create` datetime DEFAULT NULL COMMENT '创建时间',
  `Ur_Alter` varchar(30) DEFAULT NULL COMMENT '修改者',
  `Date_Alter` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Role_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 系统角色表';
