CREATE TABLE `Sys_User` (
  `Res_Code` varchar(30) NOT NULL COMMENT '资源编码',
  `Res_Name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `Res_Type` varchar(30) DEFAULT NULL COMMENT '类型',
  `Res_Content` varchar(200) DEFAULT NULL COMMENT '资源内容',
  `Mem` varchar(2000) DEFAULT NULL COMMENT '备注',
  `Ur_Create` varchar(30) DEFAULT NULL COMMENT '创建者',
  `Date_Create` datetime DEFAULT NULL COMMENT '创建时间',
  `Ur_Alter` varchar(30) DEFAULT NULL COMMENT '修改者',
  `Date_Alter` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`User_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';
