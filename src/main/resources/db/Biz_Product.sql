CREATE TABLE `Biz_Product` (
  `Prod_Code` varchar(30) NOT NULL COMMENT '产品编码',
  `Prod_Name` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `Prod_Date` datetime DEFAULT NULL COMMENT '产品生成日期',
  `Mem` varchar(2000) DEFAULT NULL COMMENT '备注',
  `Ur_Create` varchar(30) DEFAULT NULL COMMENT '创建者',
  `Date_Create` datetime DEFAULT NULL COMMENT '创建时间',
  `Ur_Alter` varchar(30) DEFAULT NULL COMMENT '修改者',
  `Date_Alter` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Prod_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';
