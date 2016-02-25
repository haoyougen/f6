DELIMITER $$  
DROP FUNCTION IF EXISTS `nextval`$$  
  
CREATE DEFINER=`root`@`%` FUNCTION `nextval`(seq_name varchar(50)) RETURNS int(11)  
BEGIN  
UPDATE sys_sequence  
SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT  
where name=seq_name;  
return currval(seq_name);  
END$$