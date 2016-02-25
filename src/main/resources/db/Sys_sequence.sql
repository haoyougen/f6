CREATE TABLE `sys_sequence` (  
   `NAME` varchar(50) NOT NULL,  
   `CURRENT_VALUE` int(11) NOT NULL DEFAULT '0',  
   `INCREMENT` int(11) NOT NULL DEFAULT '1',  
   PRIMARY KEY (`NAME`)  
 )