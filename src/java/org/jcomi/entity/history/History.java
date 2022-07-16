/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jcomi.entity.history;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Data;
import org.jcomi.entity.DataTransferObject;

/**
 *
 * @author Thai Binh Quoc Viet-CE160378
 */
@Data
public class History implements DataTransferObject {
    private int id,accountId, chapterId;
    private long readDate;

    public History() {
        id=0;
        accountId = 0 ;
        chapterId = 0 ;
        readDate = 0 ; 
        
    }
   
    
    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("ID");
        accountId = resultSet.getInt("Account_ID");
        chapterId = resultSet.getInt("Chapter_ID");
        readDate = resultSet.getLong("Read_Date");
        
    }
    
}
