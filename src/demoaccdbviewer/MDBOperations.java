/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoaccdbviewer;
import com.healthmarketscience.jackcess.*;
import java.io.*;
/**
 *
 * @author Graf_Nameless
 */
public class MDBOperations {
    public static String[] GetRowValueOnNameColumn(Table tab, String columnName) throws IOException
    {
        String[] res = new String[tab.getRowCount()];
        int countRow = 0;
        for(Row r : tab)
        {            
            res[countRow] = String.valueOf(r.get(columnName));
            countRow++;
        }
        return res;
    }
    
    public static void UpdateRowValue(Table tab) throws IOException
    {
        /*Курсор по таблице*/
        Cursor cur = CursorBuilder.createCursor(tab);
        /*Цикл по строкам с условием, что в колонке Код значение = 1*/
        for(Row r : cur.newIterable().addMatchPattern("Код", 1)){
            /*Обновляем колонку с именем FirstName*/
            r.put("FirstName", "Денис");
            tab.updateRow(r);            
        }
    }
}
