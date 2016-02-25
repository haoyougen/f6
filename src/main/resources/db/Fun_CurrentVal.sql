    DELIMITER $$  
      
    DROP FUNCTION IF EXISTS `currval`$$  
      
    CREATE DEFINER=`root`@`%` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS INT(11)  
    BEGIN  
    DECLARE VALUE INTEGER;  
    SET VALUE=0;  
    SELECT current_value INTO VALUE  
    FROM sys_sequence   
    WHERE NAME=seq_name;  
    RETURN VALUE;  
    END$$  
      
    DELIMITER ;  